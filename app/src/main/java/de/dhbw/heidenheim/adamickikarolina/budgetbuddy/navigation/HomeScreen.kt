package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.PaymentCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.TextIconButton

@Preview
@Composable
fun HomeScreen(){
    Column{
        SavingsCard()
        TextIconButton(
            stringResource(R.string.payments_name),
            stringResource(R.string.paymentsButton_description),
            {})
        PaymentCard()
        PaymentCard()
    }
}