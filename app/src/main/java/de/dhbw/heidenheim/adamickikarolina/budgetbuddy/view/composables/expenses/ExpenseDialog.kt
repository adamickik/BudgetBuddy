package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.expenses

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates.CustomOutlinedTextField
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates.DropdownEntry
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates.DropdownMenu
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.AnalyticsViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.SavingsGoalViewModel
import java.util.Date
import java.util.Locale
import kotlin.math.abs

@Composable
fun ExpenseDialog(
    showDialog: Boolean,
    pageIndex: Int,
    onDismiss: () -> Unit,
    editingExpense: Expense? = null
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()
    val analyticsViewModel = hiltViewModel<AnalyticsViewModel>()
    val savingsGoalViewModel = hiltViewModel<SavingsGoalViewModel>()

    val savingsGoals by savingsGoalViewModel.savingsGoals.observeAsState()
    val currentSavingGoalID = savingsGoals?.get(pageIndex-1)?.sgId

    val categories by analyticsViewModel.categories.observeAsState(emptyList())
    val categoryEntries = categories.map { DropdownEntry(id = it.kId!!, name = it.kName) }

    var isDatePickerShown by remember { mutableStateOf(false) }

    var expenseTitle by remember(showDialog) { mutableStateOf(editingExpense?.eName?: "") }
    var expenseValue by remember (showDialog){ mutableStateOf(editingExpense?.eAmount?.let { ExpenseViewModel.floatToGermanCurrencyString(abs(it)) } ?: "") }
    var expenseDate by remember (showDialog){ mutableStateOf(editingExpense?.eDate ?: "") }
    var selectedCategoryId by remember(showDialog) { mutableStateOf(editingExpense?.kId) }
    var isIncome by remember(showDialog) { mutableStateOf(editingExpense?.eAmount?.let { it >= 0 } ?: true) }

    val context = LocalContext.current

    val isInputValid = remember(expenseTitle, expenseValue, expenseDate, selectedCategoryId) {
        expenseTitle.isNotEmpty() && expenseViewModel.isValidTitle(expenseTitle) &&
                expenseValue.isNotEmpty() && expenseViewModel.isValidValue(expenseValue) &&
                expenseDate.isNotEmpty()  && expenseViewModel.isValidDueDate(expenseDate) && selectedCategoryId != null
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                text = if (editingExpense != null)
                    stringResource(id = R.string.expenseDialog_edit_name)
                else
                    stringResource(id = R.string.expenseDialog_name)
            )},
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isIncome)
                                stringResource(id = R.string.expenseDialog_isIncome)
                            else
                                stringResource(id = R.string.expenseDialog_isExpense),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isIncome,
                            onCheckedChange = { isIncome = it }
                        )
                    }
                    CustomOutlinedTextField(
                        value = expenseTitle,
                        onValueChange = { expenseTitle = it },
                        label = stringResource(id = R.string.expenseDialog_title),
                        isError = expenseTitle.isNotEmpty() && !expenseViewModel.isValidTitle(expenseTitle)
                    )
                    CustomOutlinedTextField(
                        value = expenseValue,
                        onValueChange = { expenseValue = it },
                        label = stringResource(id = R.string.savingGoalDialog_value),
                        isError = expenseValue.isNotEmpty() && !expenseViewModel.isValidValue(expenseValue),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    DropdownMenu(
                        entries = categoryEntries,
                        selectedEntryId = selectedCategoryId,
                        onEntrySelected = { selectedId ->
                            selectedCategoryId = selectedId
                        },
                        defaultDisplayText = stringResource(id = R.string.expenseDialog_category)
                    )

                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = expenseDate,
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.expenseDialog_date)) },
                            readOnly = true,
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            isError = expenseDate.isNotEmpty() && !expenseViewModel.isValidDueDate(expenseDate)

                        )
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .height(IntrinsicSize.Max)
                        )
                        {
                            IconButton(
                                onClick = {
                                    if(!isDatePickerShown){
                                        val constraintsBuilder = CalendarConstraints.Builder().apply {
                                            val today = MaterialDatePicker.todayInUtcMilliseconds()
                                            setEnd(today)
                                            setValidator(DateValidatorPointBackward.now())
                                        }

                                        val datePicker = MaterialDatePicker.Builder.datePicker()
                                            .setTitleText("Zahlungsdatum")
                                            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                                            .setCalendarConstraints(constraintsBuilder.build())
                                            .build()

                                        isDatePickerShown = true
                                        datePicker.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")

                                        datePicker.addOnPositiveButtonClickListener { selection ->
                                            expenseDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(selection))
                                            isDatePickerShown = false
                                        }

                                        datePicker.addOnDismissListener {
                                            isDatePickerShown = false
                                        }
                                    }


                            }) {
                                Icon(
                                    Icons.Filled.DateRange,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = stringResource(id = R.string.expenseDialog_button_date)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountFloat = expenseViewModel.convertGermanCurrencyStringToFloat(expenseValue) * if (isIncome) 1 else -1

                        if (editingExpense != null){
                            editingExpense.eName = expenseTitle
                            editingExpense.eAmount = amountFloat
                            editingExpense.eDate = expenseDate
                            editingExpense.kId = selectedCategoryId

                            expenseViewModel.editExpense(editingExpense)
                        }
                        else
                            expenseViewModel.addExpenseAssignment(expenseTitle, amountFloat, expenseDate, currentSavingGoalID!!, selectedCategoryId!!)
                        onDismiss()
                    },
                    enabled = isInputValid
                ) {
                    Text(
                        text = if (editingExpense != null)
                            stringResource(id = R.string.expenseDialog_button_edit)
                        else
                            stringResource(id = R.string.expenseDialog_button_add)
                    )
                }
                if(editingExpense != null){
                    Button(
                        onClick = {
                            expenseViewModel.deleteExpense(editingExpense)
                            onDismiss()
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.expenseDialog_button_delete)
                        )
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(stringResource(id = R.string.expenseDialog_button_dismiss))
                }
            }
        )
    }
}