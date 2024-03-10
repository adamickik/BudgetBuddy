package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

@Composable
fun SavingTipsDialog(
    onDismissRequest:() -> Unit,
    tipp: String
) {
    AlertDialog(
        onDismissRequest = {onDismissRequest()},
        title = { Text(stringResource(R.string.savingTips_name)) },
        
        // TODO: Change with actual tips
        text = { Text(tipp) },
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.savingTips_button))
            }
        }
    )

}
