package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense

object InitialExpenseData {
    val initialExpenses = listOf(
        Expense(eName ="Lohn", eAmount = 10000.0F, eDate="10.03.2024", eAssignment=1),
        Expense(eName ="Miete", eAmount = -400.00F, eDate="11.03.2024", eAssignment=1),
        Expense(eName ="Auto", eAmount = 50.00F, eDate="20.03.2024", eAssignment=2),
    )}
