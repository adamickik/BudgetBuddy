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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

@Preview
@Composable
fun SavingsGoalCard(){
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
                    // TODO: Change with actual payment title
                    text = "AUTO",
                    style = MaterialTheme.typography.headlineMedium,

                    modifier = Modifier
                        .padding(start = 10.dp,
                            top = 10.dp,
                            end = 10.dp)
                        .weight(1f),
                )
                Text(
                    // TODO: change with actual savings goal
                    text = "15.000,00"+ stringResource(R.string.savings_currency),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(start = 10.dp,
                            top = 10.dp,
                            end = 10.dp),
                )
            }
            Text(
                text = stringResource(R.string.savingsGoal_duedate) + " 24.09.2024",
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