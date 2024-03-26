package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.WorkManager
import android.content.Context
import androidx.work.Data
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

//TODO: scheduleRepeatingDatabaseInsertJob(LocalContext, name, value, repeatDays) in Fixkosten-Dialog (wenn LocalContext nicht geht dann versuch GlobalContext)
fun scheduleRepeatingDatabaseInsertJob(context: Context, name: String, value: String, repeatDays: Long) {

    val inputData = Data.Builder()
        .putString("name", name)
        .putString("value", value)
        .build()

    val workRequest = PeriodicWorkRequestBuilder<DatabaseInsertWorker>(repeatDays, TimeUnit.DAYS)
        .setInputData(inputData)
        .build()

    WorkManager.getInstance(context).enqueue(workRequest)
}