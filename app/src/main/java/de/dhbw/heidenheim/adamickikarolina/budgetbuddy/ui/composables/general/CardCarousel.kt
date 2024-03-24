package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.PaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.ExpenseCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingDepotCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardCarousel(
    savingsGoals: List<SavingGoal>,
    onAssignButtonClick: () -> Unit
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()
    val pagerState = rememberPagerState(pageCount = { savingsGoals.size + 1 })
    var showPaymentDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember{ mutableStateOf<Expense?>(null) }

    val expensesByAssignmentId: LiveData<List<Expense>> = expenseViewModel.getExpensesByAssignmentIdSorted(pagerState.currentPage)
    val importantExpenses = expensesByAssignmentId.observeAsState(initial = emptyList()).value


    DotsIndicator(
        totalDots = savingsGoals.size + 1,
        selectedIndex = pagerState.currentPage
    )

    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            0 -> {
                val savingsDepotSum: Float by expenseViewModel.getSumOfExpensesByAssigmentID(
                    assignmentId = 0
                ).observeAsState(0f)

                SavingDepotCard(
                    savingsDepotSum = savingsDepotSum,
                    onAssignButtonClick = onAssignButtonClick
                )
            }

            else -> {
                val savingsGoal = savingsGoals[page - 1]
                val savingsGoalSum by expenseViewModel.getSumOfExpensesByAssigmentID(assignmentId = savingsGoal.sgId!!).observeAsState(0f)
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
        onIconClick = {
            selectedExpense = null
            showPaymentDialog = true})

    if (showPaymentDialog) {
        PaymentDialog(
            showDialog = showPaymentDialog,
            onDismiss = { showPaymentDialog = false },
            editingExpense = selectedExpense,
        )
    }

    LazyColumn() {
        items(importantExpenses) { expense ->
            ExpenseCard(
                expense = expense,
                onCardClick = { clickedExpense ->
                    selectedExpense = clickedExpense
                    showPaymentDialog=true
                }
            )
        }
    }
}


