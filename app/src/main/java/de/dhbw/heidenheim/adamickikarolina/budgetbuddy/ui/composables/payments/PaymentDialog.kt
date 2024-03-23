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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.google.android.material.datepicker.MaterialDatePicker
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.DatePickerInput
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import java.util.Date
import java.util.Locale

@Composable
fun PaymentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    editingExpense: Expense? = null
) {
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()

    var isDatePickerShown by remember { mutableStateOf(false) }
    var paymentTitle by remember(showDialog) { mutableStateOf(editingExpense?.eName?: "") }
    var paymentValue by remember (showDialog){ mutableStateOf(editingExpense?.eAmount?.toString() ?: "")  }
    var paymentDate by remember (showDialog){ mutableStateOf(editingExpense?.eDate ?: "") }
    val context = LocalContext.current

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
                    OutlinedTextField(
                        value = paymentTitle,
                        modifier=Modifier.padding(bottom=8.dp),
                        onValueChange = { paymentTitle = it },
                        label = { Text(stringResource(id = R.string.addPaymentDialog_title)) },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = paymentValue,
                        modifier=Modifier.padding(bottom=8.dp),
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("^-?\\d{1,3}(\\.\\d{3})*(,\\d{0,2})?$"))) {
                                paymentValue = newValue
                            }},
                        label = { Text(stringResource(id = R.string.addPaymentDialog_value))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    /* TODO Add Category DropDown
                    CategoryDropDown()*/

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
                            modifier = Modifier.weight(1f)
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
                                        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Zahlungsdatum").build()
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

                    /* TODO FINISH DATEPICKER COMPOSABLE
                    DatePickerInput(
                        labelText = stringResource(id = R.string.addPaymentDialog_date),
                        selectedDate = paymentDate,
                        onDateSelected = { newDate ->
                            paymentDate = newDate
                        }
                    )*/
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingExpense != null){
                            editingExpense.eName = paymentTitle
                            editingExpense.eAmount = paymentValue.toFloat()
                            editingExpense.eDate = paymentDate

                            expenseViewModel.editExpense(editingExpense)
                        }
                        else
                            expenseViewModel.addExpense(paymentTitle, paymentValue, paymentDate)
                        onDismiss()
                    }
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