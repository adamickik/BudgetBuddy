package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.CustomOutlinedTextField
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.DropdownEntry
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.DropdownMenu
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@Composable
fun AssignmentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()
    val savingsGoalViewModel = hiltViewModel<SavingsGoalViewModel>()

    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState(emptyList())
    val filteredSavingsGoals = savingsGoals.drop(1)

    val savingGoalEntries = filteredSavingsGoals.map { DropdownEntry(it.sgId!!, it.sgName) }
    var selectedGoalId by remember { mutableStateOf(savingGoalEntries.firstOrNull()?.id) }

    val savingDepotSum by expenseViewModel.getSumOfExpensesByAssignmentID(assignmentId = 1)
        .observeAsState(0f)

    //TODO: Code hier irgendwie schöner machen
    var selectedGoalSum = 0f
    var selectedGoalAmount = 0f
    selectedGoalId?.let {
        val selectedGoalSumInit by expenseViewModel.getSumOfExpensesByAssignmentID(assignmentId = it)
            .observeAsState(0f)
        selectedGoalSum = selectedGoalSumInit
        val selectedGoal by savingsGoalViewModel.getSavingGoalById(selectedGoalId!!)
            .observeAsState()
        selectedGoalAmount = selectedGoal?.sgGoalAmount ?: 0f
    }

    var assignmentValue by remember { mutableStateOf("") }
    val isInputValid = remember(assignmentValue) {
        assignmentValue.isNotEmpty() && expenseViewModel.isValidAssignmentValue(
            paymentValue = assignmentValue,
            savingDepotSum = savingDepotSum,
            selectedGoalSum = selectedGoalSum,
            selectedGoalRemainingAmount = selectedGoalAmount
        ) && selectedGoalId != null
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(id = R.string.assignmentDialog_name)) },
            text = {
                Column {
                    DropdownMenu(
                        entries = savingGoalEntries,
                        selectedEntryId = selectedGoalId,
                        onEntrySelected = { selectedId -> selectedGoalId = selectedId },
                        defaultDisplayText = stringResource(id = R.string.assignmentDialog_savingGoal)
                    )
                    CustomOutlinedTextField(
                        value = assignmentValue,
                        onValueChange = { assignmentValue = it },
                        label = stringResource(id = R.string.addSavingGoalDialog_value),
                        isError = assignmentValue.isNotEmpty() && !expenseViewModel.isValidAssignmentValue(
                            paymentValue = assignmentValue,
                            savingDepotSum = savingDepotSum,
                            selectedGoalSum = selectedGoalSum,
                            selectedGoalRemainingAmount = selectedGoalAmount
                        ),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                            expenseViewModel.assignExpenseToSavingsGoal(
                                assignmentValue.toFloat(),
                                selectedGoalId!!
                            )

                        onDismiss()
                    },
                    enabled = isInputValid
                ) {
                    Text(stringResource(id = R.string.assignmentDialog_addButton))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.assignmentDialog_dismissButton))
                }
            }
        )
    }
}

