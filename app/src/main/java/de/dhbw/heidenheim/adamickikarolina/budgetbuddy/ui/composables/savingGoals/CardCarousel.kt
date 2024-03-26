package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.switchMap
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.expenses.ExpenseCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.expenses.ExpenseDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.ConfirmDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.DotsIndicator
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CardCarousel(
    onAssignButtonClick: () -> Unit
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()
    val savingsGoalViewModel = hiltViewModel<SavingsGoalViewModel>()

    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())

    val pagerState = rememberPagerState(pageCount = { savingsGoals.size})
    var showPaymentDialog by remember { mutableStateOf(false) }
    var selectedExpense by remember{ mutableStateOf<Expense?>(null) }

    val expensesByAssignmentId: LiveData<List<Expense>> = savingsGoalViewModel.savingsGoals.switchMap { savingsGoals ->
        expenseViewModel.getExpensesByAssignmentIdSorted(savingsGoals[pagerState.currentPage].sgId!!)

    }
    val importantExpenses = expensesByAssignmentId.observeAsState(initial = emptyList()).value

    var showFulfilledDialog by remember { mutableStateOf(false) }
    var showExpiredDialog by remember { mutableStateOf(false) }

    DotsIndicator(
        totalDots = savingsGoals.size,
        selectedIndex = pagerState.currentPage
    )

    HorizontalPager(
        state = pagerState
    ) { page ->
        when (page) {
            0 -> {
                val savingsDepotSum: Float by expenseViewModel.getSumOfExpensesByAssignmentID(assignmentId = 1).observeAsState(0f)

                SavingDepotCard(
                    savingsDepotSum = savingsDepotSum,
                    onAssignButtonClick = onAssignButtonClick
                )
            }

            else -> {
                val savingsGoal = savingsGoals[page]
                val savingsGoalSum by expenseViewModel.getSumOfExpensesByAssignmentID(assignmentId = savingsGoal.sgId!!).observeAsState(0f)
                val remainingAmount = savingsGoal.sgGoalAmount.minus(savingsGoalSum)

                val isFulfilled by expenseViewModel.isSavingGoalFulfilled(savingsGoal.sgId!!, savingsGoal.sgGoalAmount).observeAsState(false)
                val isExpired = expenseViewModel.isSavingGoalExpired(savingsGoal)
                SavingGoalCard(
                    savingsGoal = savingsGoal,
                    remainingAmount = remainingAmount
                )

                if (isFulfilled) {
                    LaunchedEffect(key1 = isFulfilled) {
                        showFulfilledDialog = true
                    }

                    if (showFulfilledDialog) {
                        ConfirmDialog(
                            title = stringResource(id=R.string.savingsGoal_congratsTitle),
                            message=stringResource(id=R.string.savingsGoal_congrats, savingsGoal.sgName),
                            confirmButtonText = stringResource(id = R.string.savingsGoal_congratsButton),
                            onConfirm = {
                                showFulfilledDialog=false
                                savingsGoalViewModel.deleteSavingGoal(savingsGoal)
                            })
                    }
                }
                if(isExpired) {
                    LaunchedEffect(key1 = isExpired) {
                        showExpiredDialog = true
                    }

                    if (showExpiredDialog) {
                        ConfirmDialog(
                            title = stringResource(id=R.string.savingsGoal_expiredTitle),
                            message=stringResource(id=R.string.savingsGoal_expired, savingsGoal.sgName),
                            confirmButtonText = stringResource(id = R.string.savingsGoal_expiredButton),
                            onConfirm = {
                                showExpiredDialog=false
                            })
                    }
                }
            }
        }
    }

    TextIconButton(
        stringResource(R.string.payments_name),
        stringResource(R.string.paymentsButton_description),
        onIconClick = {
            selectedExpense = null
            showPaymentDialog = true})

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

    if (showPaymentDialog) {
        ExpenseDialog(
            showDialog = showPaymentDialog,
            pageIndex = pagerState.currentPage+1,
            onDismiss = { showPaymentDialog = false },
            editingExpense = selectedExpense,
        )
    }
}


