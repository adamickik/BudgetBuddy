package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseInsertWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val name = inputData.getString("name") ?: return Result.failure()
        val value = inputData.getString("value") ?: return Result.failure()

        insertIntoDatabase(name, value)
        return Result.success()
    }

    private fun insertIntoDatabase(name: String, value: String) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = dateFormat.format(Date())
        val negativeValue = (value.toFloat()*(-1)).toString()

        //TODO: expenseViewModel hier reinbringen, versteh nicht wie du das jz genau machst mit diesem hiltViewModel
        //expenseViewModel.addexpense(title = name, value = value, date = currentDate, assignment = 1)
    }
}
