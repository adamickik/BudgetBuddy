package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.AssignmentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.CardCarousel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@Composable
fun HomeScreen(
) {
    val savingsGoalViewModel = hiltViewModel<SavingsGoalViewModel>()
    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())

    var showAssignmentDialog by remember { mutableStateOf(false) }

    Column {
        CardCarousel(
            savingsGoals = savingsGoals,
            onAssignButtonClick = { showAssignmentDialog = true })

        if (showAssignmentDialog) {
            AssignmentDialog(
                savingGoals = savingsGoals,
                showDialog = showAssignmentDialog,
                onDismiss = { showAssignmentDialog = false }
            )
        }
    }
}
