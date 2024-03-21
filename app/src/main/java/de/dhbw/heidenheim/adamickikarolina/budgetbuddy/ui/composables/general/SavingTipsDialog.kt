package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.TippsViewModel

@Composable
fun SavingTipsDialog(
    onDismissRequest:() -> Unit,
    tipsViewModel: TippsViewModel
) {
    val tipp by tipsViewModel.randomTipp.observeAsState(Tipp(""))

    LaunchedEffect(Unit) {
        tipsViewModel.fetchRandomTipp()
    }

    //Log.d("SavingTipsDialog", "Aktueller Tipp: tipp.tTipp")

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.savingTips_name)) },
        text = {  Text(tipp?.tTipp ?: "Kein Tipp verfügbar")},
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.savingTips_button))
            }
        }
    )
}