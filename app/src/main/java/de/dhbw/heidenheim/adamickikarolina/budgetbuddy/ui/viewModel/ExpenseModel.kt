package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

data class ExpenseModel(
    var expenseName: String,
    var expenseAmount: Int,
    var expenseDateTime: String,
    var expenseAssignment: Int
)