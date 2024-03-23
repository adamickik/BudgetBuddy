package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDialog(
    savingGoals: List<SavingGoal>,
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()

    var assignmentValue by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var selectedGoal by remember { mutableStateOf(savingGoals.firstOrNull()) }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(id = R.string.assignmentDialog_name))},
            text = {
                Column {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                    ) {
                        TextField(
                            readOnly = true,
                            value = selectedGoal?.sgName?: "Sparziel",
                            onValueChange = { },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = expanded
                                )
                            },
                            modifier = Modifier.menuAnchor(),
                            colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            }
                        ) {
                            savingGoals.forEach { savingGoal ->
                                DropdownMenuItem(
                                    text = { Text(text = savingGoal.sgName) },
                                    onClick = {
                                        selectedGoal = savingGoal
                                        expanded = false
                                    }
                                )
                            }
                        }
                    }
                    OutlinedTextField(
                        value = assignmentValue,
                        modifier=Modifier.padding(bottom=8.dp),
                        onValueChange = { newValue ->
                            // TODO: Proper Validation for Money in ViewModel
                            if (newValue.matches(Regex("^\\d*,?\\d{0,2}$"))) {
                                assignmentValue = newValue
                            }},
                        label = { Text(stringResource(id = R.string.assignmentDialog_value))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        selectedGoal?.let {
                            expenseViewModel.assignExpenseToSavingsGoal(assignmentValue.toFloat(),
                                it
                            )
                        }
                        onDismiss()
                    }
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

