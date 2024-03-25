package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense

object InitialExpenseData {
    val initialExpenses = listOf(
        Expense(eName ="Lohn", eAmount = 10000.0F, eDate="10.03.2024", eAssignment=1, kId=1), // Angenommen kId=1 für "Einkommen"
        Expense(eName ="Miete", eAmount = -400.00F, eDate="11.03.2024", eAssignment=1, kId=2), // Angenommen kId=2 für "Feste Ausgaben"
        Expense(eName ="Lebensmittel", eAmount = -200.00F, eDate="15.03.2024", eAssignment=2, kId=3) // Angenommen kId=3 für "Variable Ausgaben"
    )
}
