package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

data class ExpenseModel(
    var expenseName: String,
    var expenseAmount: Float,
    var expenseDateTime: String,
    var expenseAssignment: Int
)