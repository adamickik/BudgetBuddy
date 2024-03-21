package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import java.util.Locale

@Composable
fun SavingsGoalCard(savingsGoal: SavingGoal){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end=15.dp,
                bottom = 10.dp),
    ) {
        Column{
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = savingsGoal.sgName,
                    style = MaterialTheme.typography.headlineMedium,

                    modifier = Modifier
                        .padding(start = 10.dp,
                            top = 10.dp,
                            end = 10.dp)
                        .weight(1f),
                )
                Text(
                    // TODO: change with actual savings goal
                    text = String.format(Locale.GERMANY, "%.2f", savingsGoal.sgGoalAmount) + stringResource(R.string.savings_currency),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(start = 10.dp,
                            top = 10.dp,
                            end = 10.dp),
                )
            }
            Text(
                text = stringResource(R.string.savingsGoal_duedate) + savingsGoal.sgDueDate,
                style = MaterialTheme.typography.bodyLarge,

                modifier = Modifier
                    .padding(start = 10.dp,
                        top = 5.dp,
                        end = 10.dp,
                        bottom=10.dp),
            )

        }

    }
}