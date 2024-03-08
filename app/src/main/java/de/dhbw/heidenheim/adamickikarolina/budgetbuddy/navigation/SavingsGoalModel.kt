package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import java.math.BigDecimal
import java.sql.Time
import java.sql.Timestamp
import java.util.Date

data class SavingsGoalModel(
    var expenseName: String,
    var expenseAmount: Int,
    var expenseDateTime: String,
    var expenseAssignment: Int
)