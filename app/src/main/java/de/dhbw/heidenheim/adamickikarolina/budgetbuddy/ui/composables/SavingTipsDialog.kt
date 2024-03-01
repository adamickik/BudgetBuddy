package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun SavingTipsDialog(
    onDismissRequest:() -> Unit
) {
    AlertDialog(
        onDismissRequest = {onDismissRequest()},
        title = { Text("Schwabentipp") },
        text = { Text("Spartipp nr. 1") },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text("OK")
            }
        }
    )

}
