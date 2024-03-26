package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals

import androidx.compose.foundation.clickable
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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import java.text.NumberFormat
import java.util.Locale

@Composable
fun SavingsGoalCard(
    savingsGoal: SavingGoal,
    onCardClick: (SavingGoal) -> Unit
){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end=15.dp,
                bottom = 10.dp)
            .clickable { onCardClick(savingsGoal)}
    ) {
        Column{
            Row(
                modifier = Modifier.fillMaxWidth()
            ){
                Text(
                    text = savingsGoal.sgName,

                    modifier = Modifier
                        .padding(start = 10.dp,
                            top = 10.dp,
                            end = 10.dp)
                        .weight(1f),
                )
                Text(
                    text = NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(savingsGoal.sgGoalAmount),
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