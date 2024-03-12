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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.AddPaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.ExpenseCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@Composable
fun HomeScreen(expenseViewModel: ExpenseViewModel, savingsGoalViewModel: SavingsGoalViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    val expenses by expenseViewModel.expenses.observeAsState(emptyList())
    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())

    Column {

        savingsGoals.forEach { savingsGoal ->
            SavingsCard(savingsGoal)
        }
            TextIconButton(
                stringResource(R.string.payments_name),
                stringResource(R.string.paymentsButton_description),
                onIconClick = { showDialog = true })
        expenses.forEach { expense ->
            ExpenseCard(expense)
        }

            if (showDialog) {
                AddPaymentDialog(
                    expenseViewModel= expenseViewModel,
                    showDialog = showDialog,
                    onDismiss = { showDialog = false },
                    onConfirmAction = { payment ->
                        showDialog = false
                        // TODO: Process payment in ViewModel
                    }
                )
            }
        }
    }
