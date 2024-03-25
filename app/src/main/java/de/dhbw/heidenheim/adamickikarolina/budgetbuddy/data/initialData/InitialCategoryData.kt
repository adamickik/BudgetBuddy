package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.Category

object InitialCategoryData {
    val initialCategories = listOf(
        Category(kName = "Lohn"),
        Category(kName = "Freizeit"),
        Category(kName = "Lebensmittel"),
        Category(kName = "Shopping"),
        Category(kName = "Haushalt")
    )
}
