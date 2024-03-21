package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LiveData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.AddPaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.ExpenseCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingDepotCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnappingLazyRow(
    expenseViewModel: ExpenseViewModel,
    savingsGoals: List<SavingGoal>,
    savingDepot: SavingDepot,
    onAssignButtonClick: () -> Unit
) {
    val savingsGoalsListSize = savingsGoals.size
    var showPaymentDialog by remember { mutableStateOf(false) }
    val pagerState = rememberPagerState(pageCount = { savingsGoalsListSize + 1 })

    val expensesByAssignmentId: LiveData<List<Expense>> = expenseViewModel.getExpensesByAssignmentId(pagerState.currentPage)
    val importantExpenses = expensesByAssignmentId.observeAsState(initial = emptyList()).value

    DotsIndicator(
        totalDots = savingsGoalsListSize + 1,
        selectedIndex = pagerState.currentPage,
        selectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unSelectedColor = MaterialTheme.colorScheme.outlineVariant,
    )

    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            0 -> SavingDepotCard(
                savingDepot = savingDepot,
                onAssignButtonClick = onAssignButtonClick
            )

            else -> {
                val savingsGoal = savingsGoals[page - 1]
                val savingsGoalSum = savingsGoal.sgId?.let {
                    expenseViewModel.getSumOfExpensesByAssigmentID(it)?.value ?: 0f
                } ?: 0f // Default to 0f if sgId is null or if getSumOfExpensesByAssignmentID returns null

                val remainingAmount = savingsGoal.sgGoalAmount.minus(savingsGoalSum)
                SavingsCard(
                    savingsGoal = savingsGoal,
                    remainingAmount = remainingAmount
                )
            }
        }
    }
        TextIconButton(
            stringResource(R.string.payments_name),
            stringResource(R.string.paymentsButton_description),
            onIconClick = { showPaymentDialog = true })


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

        importantExpenses.forEach { expense ->
            ExpenseCard(expense)
        }
    }


