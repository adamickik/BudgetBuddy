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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import java.util.Date
import java.util.Locale
import androidx.compose.material3.Icon
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.ui.Alignment
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.DBHandler
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel

@Composable
fun AddPaymentDialog(
    expenseViewModel: ExpenseViewModel,
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmAction: (String) -> Unit
) {
    // TODO: Resource Management issues with new DBHandler
    var paymentTitle by remember { mutableStateOf("") }
    var paymentValue by remember { mutableStateOf("") }
    var paymentDate by remember { mutableStateOf("") }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = { Text(stringResource(id = R.string.addPaymentDialog_name))},
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
                            // TODO: Proper Validation for Money in ViewModel
                            if (newValue.matches(Regex("^\\d*,?\\d{0,2}$"))) {
                                paymentValue = newValue
                            }},
                        label = { Text(stringResource(id = R.string.addPaymentDialog_value))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = paymentDate,
                            onValueChange = { /* Read-Only Text Field */ },
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
                                val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Zahlungsdatum").build()
                                datePicker.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")
                                datePicker.addOnPositiveButtonClickListener { selection ->
                                    paymentDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(selection))
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
                        expenseViewModel.addExpense(paymentTitle, (paymentValue).toString(), paymentDate);
                        onDismiss();
                        onConfirmAction("test")
                    }
                ) {
                    Text(stringResource(id = R.string.addPaymentDialog_addButton))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.addPaymentDialog_dismissButton))
                }
            }
        )
    }
}