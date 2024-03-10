package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DBHandler(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createExpensesTableQuery = """
            CREATE TABLE $TABLE_NAME_EXPENSES (
                $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COL TEXT,
                $AMOUNT_COL INTEGER,
                $DATETIME_COL TEXT,
                $ASSIGNMENT_COL INTEGER
            )
        """.trimIndent()

        val createSavingGoalsTableQuery = """
            CREATE TABLE $TABLE_NAME_SAVING_GOALS (
                $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COL TEXT
            )
        """.trimIndent()

        db.execSQL(createExpensesTableQuery)
        db.execSQL(createSavingGoalsTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_EXPENSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_SAVING_GOALS")
        onCreate(db)
    }

    // Add methods to interact with the savingGoals table here
    // For example: addNewSavingGoal(), readSavingGoals(), updateSavingGoal(), deleteSavingGoal()

    companion object {
        private const val DB_NAME = "savingsdb"
        private const val DB_VERSION = 2 // Make sure to increment this if you're adding the table after initial deployment

        private const val TABLE_NAME_EXPENSES = "expenses"
        private const val TABLE_NAME_SAVING_GOALS = "savingGoals"

        // Common column names
        private const val ID_COL = "id"
        private const val NAME_COL = "name"

        // Expenses table column names
        private const val AMOUNT_COL = "amount"
        private const val DATETIME_COL = "datetime"
        private const val ASSIGNMENT_COL = "assignment"
    }

    // Example method to add a new saving goal
    fun addNewSavingGoal(goalName: String) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(NAME_COL, goalName)
        }
        db.insert(TABLE_NAME_SAVING_GOALS, null, values)
        db.close()
    }

    // this method is use to add new course to our sqlite database.
    fun addNewExpense(
        expenseName: String?,
        expenseAmount: Int?,
        expenseAssignment: Int?
    ) {
        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        val db = this.writableDatabase
        // on below line we are creating a
        // variable for content values.
        val values = ContentValues()
        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, expenseName)
        values.put(AMOUNT_COL, expenseAmount)

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)
        values.put(DATETIME_COL, formattedDateTime)

        values.put(ASSIGNMENT_COL, expenseAssignment)

        var result = db.insert(TABLE_NAME_EXPENSES, null, values)
        //if(result == -1.toLong())
        // ERROR

        db.close()
    }


    // we have created a new method for reading all the courses.
    fun readExpenses(): ArrayList<ExpenseModel> {
        // on below line we are creating a database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorExpenses: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_EXPENSES", null)

        // on below line we are creating a new array list.
        val expenseModelArrayList: ArrayList<ExpenseModel> = ArrayList()

        // moving our cursor to first position.
        if (cursorExpenses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                expenseModelArrayList.add(
                    ExpenseModel(
                        cursorExpenses.getString(1), // expenseName
                        cursorExpenses.getInt(2),    // expenseAmount (This might be incorrect if it's actually expenseAssignment)
                        cursorExpenses.getString(3), // expenseDateTime
                        cursorExpenses.getInt(4)     // expenseAssignment (This might be incorrect if it's actually expenseAmount)
                    )
                )
            } while (cursorExpenses.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorExpenses.close()
        return expenseModelArrayList
    }
    fun readSavingsGoals(): ArrayList<SavingsGoalModel> {
        // Create a database instance for reading data.
        val db = this.readableDatabase

        // Execute a query to get all data from the savingGoals table.
        val cursorSavingGoals: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_SAVING_GOALS", null)

        // Initialize an empty ArrayList to hold the saving goal models.
        val savingGoalModelArrayList: ArrayList<SavingsGoalModel> = ArrayList()

        // Iterate through the cursor results and populate the ArrayList.
        if (cursorSavingGoals.moveToFirst()) {
            do {
                // Extract id and name for each row in the cursor.
                val id = cursorSavingGoals.getInt(0)
                val name = cursorSavingGoals.getString(1)

                // Create a new SavingGoalModel instance and add it to the ArrayList.
                savingGoalModelArrayList.add(SavingsGoalModel(id, name))
            } while (cursorSavingGoals.moveToNext()) // Move to the next row.
        }
        // Close the cursor to release resources.
        cursorSavingGoals.close()

        // Return the filled ArrayList.
        return savingGoalModelArrayList
    }
    fun insertInitialDataIfNeeded() {
        val expenseList = this.readExpenses()
        if (expenseList.isNullOrEmpty()) {
            // Insert initial data
            this.addNewExpense("Overwatch Skins", 150, 1)
            this.addNewExpense("Fortnite Skins", 90, 2)
            // Add more as needed
        }
        val savingsGoalList = this.readSavingsGoals()
        if (savingsGoalList.isNullOrEmpty()){
            this.addNewSavingGoal("Auto")
            this.addNewSavingGoal("Haus")
        }
    }
}