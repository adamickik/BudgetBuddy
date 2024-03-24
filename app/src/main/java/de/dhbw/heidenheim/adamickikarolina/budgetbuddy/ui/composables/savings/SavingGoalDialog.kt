package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings

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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel
import java.util.Date
import java.util.Locale

// TODO Check functionality of SavingGoalDialog

@Composable
fun SavingGoalDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    editingSavingGoal: SavingGoal? = null
) {
    val savingGoalViewModel = hiltViewModel<SavingsGoalViewModel>()

    var isDatePickerShown by remember { mutableStateOf(false) }
    var savingGoalTitle by remember (showDialog){ mutableStateOf(editingSavingGoal?.sgName?:"") }
    var savingGoalValue by remember (showDialog){ mutableStateOf(editingSavingGoal?.sgGoalAmount?.toString() ?:"") }
    var savingGoalDueDate by remember (showDialog){ mutableStateOf(editingSavingGoal?.sgDueDate?:"") }
    val context = LocalContext.current

    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = {
                Text(
                    text= if (editingSavingGoal!= null)
                        stringResource(id = R.string.addSavingGoalDialog_editName)
                    else
                        stringResource(id = R.string.addSavingGoalDialog_name)
                )},
            text = {
                Column {
                    OutlinedTextField(
                        value = savingGoalTitle,
                        modifier=Modifier.padding(bottom=8.dp),
                        onValueChange = { savingGoalTitle = it },
                        label = { Text(stringResource(id = R.string.addSavingGoalDialog_title)) },
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = savingGoalValue,
                        modifier=Modifier.padding(bottom=8.dp),
                        onValueChange = { newValue ->
                            if (newValue.matches(Regex("^\\d*,?\\d{0,2}$"))) {
                                savingGoalValue = newValue
                            }},
                        label = { Text(stringResource(id = R.string.addSavingGoalDialog_value))},
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                    )
                    Row(
                        modifier = Modifier.padding(bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        OutlinedTextField(
                            value = savingGoalDueDate,
                            onValueChange = {},
                            label = { Text(stringResource(id = R.string.addSavingGoalDialog_duedate)) },
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
                                            savingGoalDueDate = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(selection))
                                            isDatePickerShown = false
                                        }

                                        datePicker.addOnDismissListener {
                                            isDatePickerShown = false
                                        }
                                    }
                                }
                            ) {
                                Icon(
                                    Icons.Filled.DateRange,
                                    modifier = Modifier.fillMaxSize(),
                                    contentDescription = stringResource(id = R.string.addSavingGoalDialog_dueDateButton)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (editingSavingGoal != null){
                            editingSavingGoal.sgName = savingGoalTitle
                            editingSavingGoal.sgGoalAmount = savingGoalValue.toFloat()
                            editingSavingGoal.sgDueDate = savingGoalDueDate

                            savingGoalViewModel.editSavingGoal(editingSavingGoal)
                        }
                        else{
                            savingGoalViewModel.addSavingsGoal(savingGoalTitle, savingGoalValue, savingGoalDueDate)
                        }
                        onDismiss()
                    }
                ){
                    Text(
                        text = if (editingSavingGoal != null)
                            stringResource(id = R.string.addSavingGoalDialog_editButton)
                        else
                            stringResource(id = R.string.addSavingGoalDialog_addButton)
                    )
                }
                if(editingSavingGoal!=null){
                    Button(
                        onClick = {
                            savingGoalViewModel.deleteSavingGoal(editingSavingGoal)
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
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.addSavingGoalDialog_dismissButton))
                }
            }
        )
    }
}