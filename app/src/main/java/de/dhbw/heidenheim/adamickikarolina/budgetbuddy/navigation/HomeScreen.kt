package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.AssignmentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SnappingLazyRow
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingDepotViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@Composable
fun HomeScreen(
    expenseViewModel: ExpenseViewModel,
    savingsGoalViewModel: SavingsGoalViewModel,
    savingDepotViewModel: SavingDepotViewModel
) {
    var showAssignmentDialog by remember { mutableStateOf(false) }
    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())
    val savingDepot by savingDepotViewModel.savingDepot.observeAsState(SavingDepot(0.0f))

    Column {
        SnappingLazyRow(
            expenseViewModel = expenseViewModel,
            savingsGoals = savingsGoals,
            savingDepot=savingDepot,
            onAssignButtonClick = { showAssignmentDialog = true })

        if (showAssignmentDialog) {
            AssignmentDialog(
                expenseViewModel= expenseViewModel,
                savingGoals = savingsGoals,
                showDialog = showAssignmentDialog,
                onDismiss = { showAssignmentDialog = false },
                onConfirmAction = { payment ->
                    showAssignmentDialog = false
                    // TODO: Process payment in ViewModel
                }
            )
        }
    }
}
