package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

@Composable
fun AddPaymentDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirmAction: (String) -> Unit
) {
    if (showDialog) {
        AlertDialog(
            modifier = Modifier.fillMaxWidth(),
            onDismissRequest = { onDismiss() },
            title = { stringResource(id = R.string.addPaymentsDialog_name) },
            text = {
                Column {
                    // TODO: Zahlungsinfos angeben
                    Text("Geben Sie Details zur Zahlung ein")
                }
            },
            confirmButton = {
                Button(onClick = { onConfirmAction("BeispielZahlung") }) {
                    Text(stringResource(id = R.string.addPaymentsDialog_addButton))
                }
            },
            dismissButton = {
                Button(onClick = { onDismiss() }) {
                    Text(stringResource(id = R.string.addPaymentsDialog_dismissButton))
                }
            }
        )
    }
}