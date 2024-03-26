package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments

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
import androidx.compose.runtime.mutableIntStateOf
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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.CategoryDropDown
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.CustomOutlinedTextField
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import java.util.Date
import java.util.Locale
import kotlin.math.abs

// TODO Change to Switch


@Composable
fun PaymentDialog(
    showDialog: Boolean,
    pageIndex: Int,
    onDismiss: () -> Unit,
    editingExpense: Expense? = null
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()

    var isDatePickerShown by remember { mutableStateOf(false) }
    var paymentTitle by remember(showDialog) { mutableStateOf(editingExpense?.eName?: "") }
    var paymentValue by remember (showDialog){ mutableStateOf(editingExpense?.eAmount?.let { ExpenseViewModel.floatToGermanCurrencyString(abs(it)) } ?: "") }
    var paymentDate by remember (showDialog){ mutableStateOf(editingExpense?.eDate ?: "") }
    var selectedCategoryId by remember(showDialog) { mutableStateOf(editingExpense?.kId) }
    var isIncome by remember(showDialog) { mutableStateOf(editingExpense?.eAmount?.let { it >= 0 } ?: true) }

    val context = LocalContext.current

    val isInputValid = remember(paymentTitle, paymentValue, paymentDate, selectedCategoryId) {
        paymentTitle.isNotEmpty() && expenseViewModel.isValidTitle(paymentTitle) &&
                paymentValue.isNotEmpty() && expenseViewModel.isValidValue(paymentValue) &&
                paymentDate.isNotEmpty()  && expenseViewModel.isValidDueDate(paymentDate) && selectedCategoryId != null
    }

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                text = if (editingExpense != null)
                    stringResource(id = R.string.addPaymentDialog_editName)
                else
                    stringResource(id = R.string.addPaymentDialog_name)
            )},
            text = {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = if (isIncome)
                                stringResource(id = R.string.addPaymentDialog_isIncome)
                            else
                                stringResource(id = R.string.addPaymentDialog_isExpense),
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = isIncome,
                            onCheckedChange = { isIncome = it }
                        )
                    }
                    CustomOutlinedTextField(
                        value = paymentTitle,
                        onValueChange = { paymentTitle = it },
                        label = stringResource(id = R.string.addPaymentDialog_title),
                        isError = paymentTitle.isNotEmpty() && !expenseViewModel.isValidTitle(paymentTitle)
                    )
                    CustomOutlinedTextField(
                        value = paymentValue,
                        onValueChange = { paymentValue = it },
                        label = stringResource(id = R.string.addSavingGoalDialog_value),
                        isError = paymentValue.isNotEmpty() && !expenseViewModel.isValidValue(paymentValue),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    CategoryDropDown(
                        selectedCategoryId= selectedCategoryId,
                        onCategorySelected = { categoryId ->
                            selectedCategoryId = categoryId!!
                        },
                    )

                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = paymentDate,
                            onValueChange = { },
                            label = { Text(stringResource(id = R.string.addPaymentDialog_date)) },
                            readOnly = true,
                            singleLine = true,
                            modifier = Modifier.weight(1f),
                            isError = paymentDate.isNotEmpty() && !expenseViewModel.isValidDueDate(paymentDate)

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
                                            paymentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(selection))
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
                                    contentDescription = stringResource(id = R.string.addPaymentDialog_dateButton)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val amountFloat = expenseViewModel.convertGermanCurrencyStringToFloat(paymentValue) * if (isIncome) 1 else -1

                        if (editingExpense != null){
                            editingExpense.eName = paymentTitle
                            editingExpense.eAmount = amountFloat
                            editingExpense.eDate = paymentDate
                            editingExpense.kId = selectedCategoryId

                            expenseViewModel.editExpense(editingExpense)
                        }
                        else
                            expenseViewModel.addExpenseAssignment(paymentTitle, amountFloat, paymentDate, pageIndex, selectedCategoryId!!)
                        onDismiss()
                    },
                    enabled = isInputValid
                ) {
                    Text(
                        text = if (editingExpense != null)
                            stringResource(id = R.string.addPaymentDialog_editButton)
                        else
                            stringResource(id = R.string.addPaymentDialog_addButton)
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
                            text = stringResource(id = R.string.addPaymentDialog_deleteButton)
                        )
                    }
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(stringResource(id = R.string.addPaymentDialog_dismissButton))
                }
            }
        )
    }
}