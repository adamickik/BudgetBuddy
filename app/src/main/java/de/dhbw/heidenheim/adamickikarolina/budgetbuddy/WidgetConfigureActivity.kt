package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.AppDatabase
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.ExpenseDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoalDao

class WidgetConfigureActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var listView: ListView
    private lateinit var savingGoalDao: SavingGoalDao
    private lateinit var expenseDao: ExpenseDao

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.budget_buddy_widget_configure)

        val db = Room.databaseBuilder(this, AppDatabase::class.java, "budgetbuddyDB")
            .allowMainThreadQueries()
            .build()

        savingGoalDao = db.getSavingGoalDao()
        expenseDao = db.getExpenseDao()

        // Find ListView in Layout
        listView = findViewById(R.id.lvSavingGoals)

        // Read intent that started activity
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // Stop activity if started without valid widget id
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        val savingGoalsList = savingGoalDao.getAllOffline()
        val savingGoalsListName = savingGoalsList.map { savingGoal -> savingGoal.sgName }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, savingGoalsListName)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedGoal = savingGoalsList[position]

            // Save SavingGoalID
            val prefs = getSharedPreferences("widgetPrefs", Context.MODE_PRIVATE)
            prefs.edit().putString("WIDGET_$appWidgetId", selectedGoal.sgId.toString()).apply()

            val appWidgetManager = AppWidgetManager.getInstance(this@WidgetConfigureActivity)
            updateAppWidget(this@WidgetConfigureActivity, appWidgetManager, appWidgetId, savingGoalDao, expenseDao)

            // Reply result and stop activity
            val resultValue = Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }
}
