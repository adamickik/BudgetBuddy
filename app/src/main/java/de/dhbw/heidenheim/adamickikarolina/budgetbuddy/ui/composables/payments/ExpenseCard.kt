package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Expense
import java.util.Locale

@Composable
fun ExpenseCard(expense: Expense) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 10.dp
            ),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                // TODO: Change with actual expense title
                text = expense.eName,

                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
                    .weight(1f),
            )
            Text(
                // TODO: change with actual payment
                text = String.format(Locale.GERMANY, "%.2f", expense.eAmount)+ stringResource(R.string.savings_currency),
                modifier = Modifier
                    .padding(
                        start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    ),
            )
        }
    }
}