package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.PaymentCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.ProfileCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.SavingsGoalCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.TextIconButton

@Preview
@Composable
fun ProfileScreen(){
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
            {})
        PaymentCard()
    }
}