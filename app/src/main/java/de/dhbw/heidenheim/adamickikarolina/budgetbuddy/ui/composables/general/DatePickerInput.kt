package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange

@Composable
fun DatePickerInput(
    labelText: String,
    selectedDate: String,
    onDateSelected: (String) -> Unit,
) {
    val context = LocalContext.current
    var isDatePickerShown by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        OutlinedTextField(
            value = selectedDate,
            onValueChange = { /* Read-Only Text Field */ },
            label = { Text(labelText) },
            readOnly = true,
            singleLine = true,
            modifier = Modifier.weight(1f)
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .height(IntrinsicSize.Max)
        ) {
            IconButton(
                onClick = {
                    if (!isDatePickerShown) {
                        val datePicker = MaterialDatePicker.Builder.datePicker().setTitleText("Zahlungsdatum").build()
                        datePicker.show((context as androidx.fragment.app.FragmentActivity).supportFragmentManager, "DATE_PICKER")
                        isDatePickerShown = true

                        datePicker.addOnPositiveButtonClickListener { selection ->
                            onDateSelected(SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(selection)))
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
                    contentDescription = "Datum auswählen"
                )
            }
        }
    }
}