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

@Preview
@Composable
fun ProfileScreen(){
    var showPaymentDialog by remember{ mutableStateOf(false) }
    var showSavingGoalDialog by remember{ mutableStateOf(false) }

    Column {
        ProfileCard()
        TextIconButton(
            stringResource(R.string.savingsGoals_name),
            stringResource(R.string.savingsGoalsButton_description),
            onIconClick = {showSavingGoalDialog=true})
        SavingsGoalCard()
        TextIconButton(
            stringResource(R.string.fixedPayments_name),
            stringResource(R.string.fixedPaymentsButton_description),
            onIconClick = {showPaymentDialog=true})
        PaymentCard()
    }

    if(showPaymentDialog){
        AddPaymentDialog(
            showDialog = showPaymentDialog,
            onDismiss = { showPaymentDialog = false },
            onConfirmAction = { payment ->
                showPaymentDialog = false
                // TODO: Process fixed payment
            }
        )
    }

    if(showSavingGoalDialog){
        AddSavingGoalDialog(
            showDialog = showSavingGoalDialog,
            onDismiss = { showSavingGoalDialog = false },
            onConfirmAction = { payment ->
                showSavingGoalDialog = false
                // TODO: Process fixed payment
            }
        )
    }
}