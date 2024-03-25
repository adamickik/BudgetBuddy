package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.initialData

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal

object InitialSavingGoalData {
    val initialSavingGoals = listOf(
        SavingGoal(sgName ="Depot", sgGoalAmount = 1000.00f, sgDueDate ="31.12.2999"),
        SavingGoal(sgName ="Auto", sgGoalAmount = 1000.00f, sgDueDate ="31.03.2024"),
        SavingGoal(sgName ="Haus", sgGoalAmount = 500000.00f, sgDueDate ="31.04.2024"),
    )
}
