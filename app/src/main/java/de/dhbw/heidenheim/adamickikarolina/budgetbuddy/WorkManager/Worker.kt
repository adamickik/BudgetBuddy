package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.WorkManager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class DatabaseInsertWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        // Extract the input data
        val name = inputData.getString("name") ?: return Result.failure()
        val value = inputData.getString("value") ?: return Result.failure()

        // Perform the database insertion
        insertIntoDatabase(name, value)

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }

    private fun insertIntoDatabase(name: String, value: String) {
        // Your database insertion logic here
    }
}
