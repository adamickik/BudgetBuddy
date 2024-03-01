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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.SavingsGoalCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton

@Preview
@Composable
fun ProfileScreen(){
    var showDialog by remember{ mutableStateOf(false) }

    Column {
        ProfileCard()
        TextIconButton(
            stringResource(R.string.savingsGoals_name),
            stringResource(R.string.savingsGoalsButton_description),
            {})
        SavingsGoalCard()
        TextIconButton(
            stringResource(R.string.fixedPayments_name),
            stringResource(R.string.fixedPaymentsButton_description),
            onIconClick = {showDialog=true})
        PaymentCard()
    }

    if(showDialog){
        AddPaymentDialog(
            showDialog = showDialog,
            onDismiss = { showDialog = false },
            onConfirmAction = { payment ->
                showDialog = false
                // TODO: Process fixed payment
            }
        )
    }
}