package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.savingGoals

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SavingGoalCard(
    savingsGoal: SavingGoal,
    remainingAmount: Float
) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                top = 15.dp,
                end = 15.dp,
                bottom = 5.dp
            )
    ) {
        Text(
            text = savingsGoal.sgName,
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 10.dp
                ),
        )
        Text(
            text = NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(savingsGoal.sgGoalAmount.minus(remainingAmount)),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(
                    top = 5.dp,
                    bottom = 5.dp
                )
                .align(Alignment.CenterHorizontally),
        )
        Text(
            text =  stringResource(R.string.savingCard_remaining) +  NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(remainingAmount),
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top=5.dp,
                    bottom = 10.dp
                ),
        )
    }
}