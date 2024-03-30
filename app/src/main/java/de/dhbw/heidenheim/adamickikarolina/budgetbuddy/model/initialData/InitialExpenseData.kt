package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.Expense

object InitialExpenseData {
    val initialExpenses = listOf(
        Expense(eName ="Lohn", eAmount = 10000.0F, eDate="10.03.2024", eAssignment=1, kId=2),
        Expense(eName ="Miete", eAmount = -400.00F, eDate="11.03.2024", eAssignment=1, kId=5),
        Expense(eName ="Lebensmittel", eAmount = -200.00F, eDate="15.03.2024", eAssignment=1, kId=4)
    )
}
