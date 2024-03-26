package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.payments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.expense.Expense
import java.text.NumberFormat
import java.util.Locale

// TODO: payments
@Composable
fun PaymentCard(
    expense: Expense,
    onCardClick: (Expense) -> Unit
) {
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
            )
            .clickable { onCardClick(expense)}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
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
                text = NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(expense.eAmount),
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