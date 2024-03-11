package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.BudgetBuddyWidget
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalModel
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DBHandler(private val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val createExpensesTableQuery = """
            CREATE TABLE $TABLE_NAME_EXPENSES (
                $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COL TEXT,
                $AMOUNT_COL REAL,
                $DATETIME_COL DATETIME DEFAULT CURRENT_TIMESTAMP,
                $ASSIGNMENT_COL INTEGER
            )
        """.trimIndent()

        val createSavingGoalsTableQuery = """
            CREATE TABLE $TABLE_NAME_SAVING_GOALS (
                $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
                $NAME_COL TEXT,
                $GOAL_AMOUNT_COL REAL
            )
        """.trimIndent()

        val createTippsTableQuery = """
    CREATE TABLE $TABLE_NAME_TIPPS (
        $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
        $TIPP_COL TEXT
    )
""".trimIndent()


        db.execSQL(createExpensesTableQuery)
        db.execSQL(createSavingGoalsTableQuery)
        db.execSQL(createTippsTableQuery)

        executeSqlScript(db, "initial_data.sql")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_EXPENSES")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_SAVING_GOALS")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME_TIPPS")
        onCreate(db)
    }

    // Add methods to interact with the savingGoals table here
    // For example: addNewSavingGoal(), readSavingGoals(), updateSavingGoal(), deleteSavingGoal()

    companion object {
        private const val DB_NAME = "savingsdb"
        private const val DB_VERSION = 3 // Make sure to increment this if you're adding the table after initial deployment

        private const val TABLE_NAME_EXPENSES = "expenses"
        private const val TABLE_NAME_SAVING_GOALS = "savingGoals"
        private const val TABLE_NAME_TIPPS = "tipps"

        // Common column names
        private const val ID_COL = "id"
        private const val NAME_COL = "name"

        // Expenses table column names
        private const val AMOUNT_COL = "amount"
        private const val DATETIME_COL = "datetime"
        private const val ASSIGNMENT_COL = "assignment"

        //SavingsGoals table column names
        private const val GOAL_AMOUNT_COL = "goalamount"

        //Tips table column names
        private const val TIPP_COL = "tipp"
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

        values.put(ASSIGNMENT_COL, expenseAssignment)

        var result = db.insert(TABLE_NAME_EXPENSES, null, values)
        //if(result == -1.toLong())
        // ERROR

        db.close()

        val intent = Intent(context, BudgetBuddyWidget::class.java).apply {
            action = "de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ACTION_UPDATE_WIDGET"
        }
        context.sendBroadcast(intent)

    }


    // we have created a new method for reading all the courses.
    fun readExpenses(assignment: Int? = null): ArrayList<ExpenseModel> {
        val db = this.readableDatabase

        // Prepare the base query and selection arguments.
        var query = "SELECT * FROM $TABLE_NAME_EXPENSES"
        val selectionArgs = mutableListOf<String>()

        // If an assignment is provided, modify the query to include a WHERE clause.
        if (assignment != null) {
            query += " WHERE $ASSIGNMENT_COL = ?"
            selectionArgs.add(assignment.toString())
        }

        // Execute the query with possible selection arguments.
        val cursorExpenses: Cursor = if (selectionArgs.isEmpty()) {
            db.rawQuery(query, null)
        } else {
            db.rawQuery(query, selectionArgs.toTypedArray())
        }

        val expenseModelArrayList: ArrayList<ExpenseModel> = ArrayList()

        if (cursorExpenses.moveToFirst()) {
            do {
                expenseModelArrayList.add(
                    ExpenseModel(
                        cursorExpenses.getString(1), // expenseName
                        cursorExpenses.getFloat(2),    // expenseAmount (This might be incorrect if it's actually expenseAssignment)
                        cursorExpenses.getString(3), // expenseDateTime
                        cursorExpenses.getInt(4)     // expenseAssignment (This might be incorrect if it's actually expenseAmount)
                    )
                )
            } while (cursorExpenses.moveToNext())
        }

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
                val amount = cursorSavingGoals.getFloat(2)

                // Create a new SavingGoalModel instance and add it to the ArrayList.
                savingGoalModelArrayList.add(SavingsGoalModel(id, name, amount))
            } while (cursorSavingGoals.moveToNext()) // Move to the next row.
        }
        // Close the cursor to release resources.
        cursorSavingGoals.close()

        // Return the filled ArrayList.
        return savingGoalModelArrayList
    }

    fun getSumOfExpenses(assignment: Int? = null): Float {
        val db = this.readableDatabase
        val queryBuilder = StringBuilder("SELECT SUM($AMOUNT_COL) FROM $TABLE_NAME_EXPENSES")

        // If an assignment is provided, add a WHERE clause to the query
        assignment?.let {
            queryBuilder.append(" WHERE $ASSIGNMENT_COL = ?")
        }

        // Prepare the SQL query
        val query = queryBuilder.toString()

        // Execute the query. If an assignment is provided, use it as a selection argument.
        val cursor: Cursor = if (assignment != null) {
            db.rawQuery(query, arrayOf(assignment.toString()))
        } else {
            db.rawQuery(query, null)
        }

        var sum: Float = 0f
        if (cursor.moveToFirst()) {
            sum = cursor.getFloat(0) // Get the sum from the first column of the result
        }
        cursor.close()
        return sum
    }

    fun readRandomTipp(): ArrayList<String> {
        // on below line we are creating a database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorExpenses: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME_TIPPS ORDER BY RANDOM() LIMIT 1", null)

        // on below line we are creating a new array list.
        val courseModelArrayList: ArrayList<String> = ArrayList()

        // moving our cursor to first position.
        if (cursorExpenses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModelArrayList.add(cursorExpenses.getString(1))
            } while (cursorExpenses.moveToNext())
            // moving our cursor to next.
        }
        // at last closing our cursor and returning our array list.
        cursorExpenses.close()
        return courseModelArrayList
    }

    private fun executeSqlScript(db: SQLiteDatabase, fileName: String) {
        val assetManager = context.assets
        val inputStream = assetManager.open(fileName)
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val sqlScript = bufferedReader.readText()
        val sqlStatements = sqlScript.split(";")

        for (statement in sqlStatements) {
            val trimmedStatement = statement.trim()
            if (trimmedStatement.isNotEmpty()) {
                db.execSQL(trimmedStatement)
            }
        }
    }
}