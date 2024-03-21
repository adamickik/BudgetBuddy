package de.dhbw.heidenheim.adamickikarolina.budgetbuddy

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class WidgetConfigureActivity : AppCompatActivity() {

    private var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID
    private lateinit var listView: ListView

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setResult(Activity.RESULT_CANCELED)
        setContentView(R.layout.budget_buddy_widget_configure)

        // Finde die ListView im Layout
        listView = findViewById(R.id.lvSavingGoals)

        // Intent, der die Activity gestartet hat, auslesen
        val intent = intent
        val extras = intent.extras
        if (extras != null) {
            appWidgetId = extras.getInt(
                AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
        }

        // Wenn die Activity ohne gültige Widget ID gestartet wurde, beende sie
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
        }

        // Hier sollte die Liste der Saving Goals aus deiner Datenbank geladen werden
        // Dummy-Daten für dieses Beispiel
        val savingGoalsList = listOf("Goal 1", "Goal 2", "Goal 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, savingGoalsList)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedGoal = savingGoalsList[position]

            // Saving Goal ID oder ein anderer eindeutiger Identifier sollte hier gespeichert werden
            val prefs = getSharedPreferences("widgetPrefs", Context.MODE_PRIVATE)
            prefs.edit().putString("WIDGET_$appWidgetId", selectedGoal).apply()

            val appWidgetManager = AppWidgetManager.getInstance(this@WidgetConfigureActivity)
            updateAppWidget(this@WidgetConfigureActivity, appWidgetManager, appWidgetId)

            // Ergebnis zurücksenden und Activity beenden
            val resultValue = Intent().apply {
                putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            }
            setResult(Activity.RESULT_OK, resultValue)
            finish()
        }
    }
}
