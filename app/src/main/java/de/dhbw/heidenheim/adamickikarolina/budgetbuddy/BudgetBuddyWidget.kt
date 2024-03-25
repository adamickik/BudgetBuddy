package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import androidx.room.Room
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.AppDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class BudgetBuddyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        val scope = CoroutineScope(Dispatchers.IO)

        scope.launch {
            // Initialisiere die Datenbank und das DAO
            val db = Room.databaseBuilder(context, AppDatabase::class.java, "budgetbuddyDB").build()
            val savingGoalDao = db.getSavingGoalDao()
            val expenseDao = db.getExpenseDao()


            // There may be multiple widgets active, so update all of them
            for (appWidgetId in appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId, savingGoalDao, expenseDao)
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        if (intent?.action == "de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ACTION_UPDATE_WIDGET") {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val thisWidget = ComponentName(context!!, BudgetBuddyWidget::class.java)
            val appWidgetIds = appWidgetManager.getAppWidgetIds(thisWidget)
            onUpdate(context, appWidgetManager, appWidgetIds)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
    savingGoalDao: SavingGoalDao,
    expenseDao: ExpenseDao
) {
    val prefs = context.getSharedPreferences("widgetPrefs", Context.MODE_PRIVATE)
    val savingGoalId = prefs.getString("WIDGET_$appWidgetId", null)

    var topLeft = ""
    var topRight = ""

    var bottomLeft = ""
    var bottomRight = ""

    var progessValue = 0

    val views = RemoteViews(context.packageName, R.layout.budget_buddy_widget)
    if(savingGoalId != null) {
        val count = savingGoalDao.getCountById(savingGoalId.toInt());
        if(count!= 0) {
            val savingGoal = savingGoalDao.getByIdOffline(savingGoalId.toInt())
            val expense = expenseDao.getSumByAssigmentIdOffline(savingGoalId.toInt())

            // Update das Widget mit den neuen Informationen
            topLeft = savingGoal.sgName
            topRight = savingGoal.sgGoalAmount.toInt().toString() + "€"
            bottomLeft = expense.toInt().toString() + "€"
            bottomRight = (savingGoal.sgGoalAmount-expense).toInt().toString() + "€"

            progessValue = (expense/savingGoal.sgGoalAmount*100).toInt()
        } else {
            topLeft = "Goal deleted"
        }
    } else {
        topLeft = "Goal not found"
    }

    views.setTextViewText(R.id.text_top_left, topLeft)
    views.setTextViewText(R.id.text_top_right, topRight)
    views.setTextViewText(R.id.text_bottom_left, bottomLeft)
    views.setTextViewText(R.id.text_bottom_right, bottomRight)
    views.setProgressBar(R.id.widget_progress_bar, 100, progessValue, false)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}