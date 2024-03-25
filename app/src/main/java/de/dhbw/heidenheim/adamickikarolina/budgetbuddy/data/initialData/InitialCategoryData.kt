package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category

object InitialCategoryData {
    val initialCategories = listOf(
        Category(kId = 1, name = "Einkommen"),
        Category(kId = 2, name = "Feste Ausgaben"),
        Category(kId = 3, name = "Variable Ausgaben")
    )
}
