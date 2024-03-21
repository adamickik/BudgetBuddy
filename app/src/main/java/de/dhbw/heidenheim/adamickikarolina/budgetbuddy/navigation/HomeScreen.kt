package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.AssignmentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SnappingLazyRow
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.AddPaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.ExpenseCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingDepotViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@Composable
fun HomeScreen(expenseViewModel: ExpenseViewModel, savingsGoalViewModel: SavingsGoalViewModel, savingDepotViewModel: SavingDepotViewModel) {
    var showPaymentDialog by remember { mutableStateOf(false) }
    var showAssignmentDialog by remember { mutableStateOf(false) }
    val expenses by expenseViewModel.expenses.observeAsState(emptyList())
    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())
    val savingDepot by savingDepotViewModel.savingDepot.observeAsState(SavingDepot(0.0f))

    Column {
        SnappingLazyRow(
            savingsGoals = savingsGoals,
            savingDepot=savingDepot,
            onAssignButtonClick = { showAssignmentDialog = true })

        TextIconButton(
            stringResource(R.string.payments_name),
            stringResource(R.string.paymentsButton_description),
            onIconClick = { showPaymentDialog = true })

        expenses.forEach { expense ->
            ExpenseCard(expense)
        }

        if (showPaymentDialog) {
            AddPaymentDialog(
                expenseViewModel= expenseViewModel,
                showDialog = showPaymentDialog,
                onDismiss = { showPaymentDialog = false },
                onConfirmAction = { payment ->
                    showPaymentDialog = false
                    // TODO: Process payment in ViewModel
                }
            )
        }

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
