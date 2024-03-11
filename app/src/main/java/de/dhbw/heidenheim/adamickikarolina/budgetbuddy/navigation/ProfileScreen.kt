package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.AddPaymentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.PaymentCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.profile.ProfileCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsGoalCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.AddSavingGoalDialog

enum class DialogType {
    None, Payment, SavingGoal
}

@Preview
@Composable
fun ProfileScreen(){
    var currentDialog by remember { mutableStateOf(DialogType.None) }

    Column {
        ProfileCard()
        TextIconButton(
            stringResource(R.string.savingsGoals_name),
            stringResource(R.string.savingsGoalsButton_description),
            onIconClick = {currentDialog = DialogType.SavingGoal}
        )
        SavingsGoalCard()
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
            onConfirmAction = { payment ->
                currentDialog = DialogType.None
                // TODO: Delegate to ViewModel
            }
        )
        DialogType.SavingGoal -> AddSavingGoalDialog(
            showDialog = true,
            onDismiss = { currentDialog = DialogType.None },
            onConfirmAction = { savingGoal ->
                currentDialog = DialogType.None
                // TODO: Delegate to ViewModel
            }
        )
        DialogType.None -> Unit
    }
}