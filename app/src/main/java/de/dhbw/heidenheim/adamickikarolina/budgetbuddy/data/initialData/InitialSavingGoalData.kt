package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal

object InitialSavingGoalData {
    val initialSavingGoals = listOf(
        SavingGoal(sgName ="Depot", sgGoalAmount = 1000.00f, sgDueDate ="9999-12-31"),
        SavingGoal(sgName ="Test", sgGoalAmount = 1000.00f, sgDueDate ="2024-03-25"),
    )

}
