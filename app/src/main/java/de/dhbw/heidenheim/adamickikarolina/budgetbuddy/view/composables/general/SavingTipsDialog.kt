package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.general

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.SavingTipsViewModel

@Composable
fun SavingTipsDialog(
    onDismissRequest:() -> Unit,
) {
    val savingTipsViewModel = hiltViewModel<SavingTipsViewModel>()
    val tip by savingTipsViewModel.randomTip.observeAsState(Tip(""))

    LaunchedEffect(Unit) {
        savingTipsViewModel.fetchRandomTipp()
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = { Text(stringResource(R.string.savingTips_name)) },
        text = {  Text(tip?.tTipp ?: stringResource(R.string.savingTips_null))},
        confirmButton = {
            Button(onClick = { onDismissRequest() }) {
                Text(stringResource(R.string.savingTips_button))
            }
        }
    )
}