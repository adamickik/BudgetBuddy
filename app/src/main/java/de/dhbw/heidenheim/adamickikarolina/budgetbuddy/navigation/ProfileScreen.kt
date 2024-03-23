package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.AddPaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.PaymentCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.profile.ProfileCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.AddSavingGoalDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsGoalCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

enum class DialogType {
    None, Payment, SavingGoal
}

@Composable
fun ProfileScreen(
){
    val savingGoalViewModel = hiltViewModel<SavingsGoalViewModel>()
    val savingsGoals by savingGoalViewModel.savingsGoals.observeAsState(emptyList())

    var currentDialog by remember { mutableStateOf(DialogType.None) }

    Column {
        ProfileCard()
        TextIconButton(
            stringResource(R.string.savingsGoals_name),
            stringResource(R.string.savingsGoalsButton_description),
            onIconClick = {currentDialog = DialogType.SavingGoal}
        )

        LazyColumn(
            modifier = Modifier
            .height(150.dp)) {
            items(savingsGoals) { savingGoal ->
                SavingsGoalCard(savingGoal)
            }
        }

        TextIconButton(
            stringResource(R.string.fixedPayments_name),
            stringResource(R.string.fixedPaymentsButton_description),
            onIconClick = {currentDialog = DialogType.Payment}
        )
        PaymentCard()
    }

    when(currentDialog){
        DialogType.Payment -> AddPaymentDialog(
            showDialog = true,
            onDismiss = { currentDialog = DialogType.None },
            onConfirmAction = {
                // TODO better confirm action
                currentDialog = DialogType.None
            }
        )
        DialogType.SavingGoal -> AddSavingGoalDialog(
            showDialog = true,
            onDismiss = { currentDialog = DialogType.None },
            onConfirmAction = {
                currentDialog = DialogType.None
            }
        )
        DialogType.None -> Unit
    }
}