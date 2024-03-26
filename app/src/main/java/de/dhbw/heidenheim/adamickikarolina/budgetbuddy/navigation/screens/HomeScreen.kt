package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals.AssignmentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals.CardCarousel

@Composable
fun HomeScreen(
) {
    /*val savingsGoalViewModel = hiltViewModel<SavingsGoalViewModel>()

    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())
    val filteredSavingsGoals = savingsGoals.drop(1) */

    var showAssignmentDialog by remember { mutableStateOf(false) }

    Column {
        CardCarousel(
            onAssignButtonClick = { showAssignmentDialog = true })

        if (showAssignmentDialog) {
            AssignmentDialog(
                showDialog = showAssignmentDialog,
                onDismiss = { showAssignmentDialog = false }
            )
        }
    }
}
