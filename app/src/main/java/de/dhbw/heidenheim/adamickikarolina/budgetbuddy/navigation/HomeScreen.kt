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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.TextIconButton

@Preview
@Composable
fun HomeScreen(){
    var showDialog by remember{ mutableStateOf(false) }

    Column{
        SavingsCard()
        TextIconButton(
            stringResource(R.string.payments_name),
            stringResource(R.string.paymentsButton_description),
            onIconClick = {showDialog=true})
        PaymentCard()

        if(showDialog){
            AddPaymentDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirmAction = { payment ->
                    showDialog = false
                    // TODO: Process payment
                }
            )
        }
    }
}