package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DBHandler  // creating a constructor for our database handler.
    (context: Context?) :
    SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    // below method is for creating a database by running a sqlite query
    override fun onCreate(db: SQLiteDatabase) {
        // on below line we are creating an sqlite query and we are
        // setting our column names along with their data types.
        val createTableQuery = """
    CREATE TABLE $TABLE_NAME (
        $ID_COL INTEGER PRIMARY KEY AUTOINCREMENT,
        $NAME_COL TEXT,
        $AMOUNT_COL INTEGER,
        $DATETIME_COL TEXT,
        $ASSIGNMENT_COL INTEGER
    )
""".trimIndent()

        // at last we are calling a exec sql method to execute above sql query
        db.execSQL(createTableQuery)
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

        var result = db.insert(TABLE_NAME, null, values)
        //if(result == -1.toLong())
        // ERROR

        db.close()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    companion object {
        // creating a constant variables for our database.
        // below variable is for our database name.
        private const val DB_NAME = "savingsdb"

        // below int is our database version
        private const val DB_VERSION = 1

        // below variable is for our table name.
        private const val TABLE_NAME = "expenses"

        // below variable is for our id column.
        private const val ID_COL = "id"

        // below variable is for our course name column
        private const val NAME_COL = "name"

        // below variable id for our course duration column.
        private const val AMOUNT_COL = "amount"

        // below variable for our course description column.
        private const val DATETIME_COL = "datetime"

        // below variable is for our course tracks column.
        private const val ASSIGNMENT_COL = "assignment"
    }

    // we have created a new method for reading all the courses.
    fun readExpenses(): ArrayList<SavingsGoalModel> {
        // on below line we are creating a database for reading our database.
        val db = this.readableDatabase

        // on below line we are creating a cursor with query to read data from database.
        val cursorExpenses: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        // on below line we are creating a new array list.
        val courseModelArrayList: ArrayList<SavingsGoalModel> = ArrayList()

        // moving our cursor to first position.
        if (cursorExpenses.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                courseModelArrayList.add(
                    SavingsGoalModel(
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
        return courseModelArrayList
    }
    fun insertInitialDataIfNeeded() {
        val expenseList = this.readExpenses()
        if (expenseList.isNullOrEmpty()) {
            // Insert initial data
            this.addNewExpense("Groceries", 150, 1)
            this.addNewExpense("Utilities", 90, 2)
            // Add more as needed
        }
    }
}