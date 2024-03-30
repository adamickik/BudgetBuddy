package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.expenses

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
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.expense.Expense
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ExpenseCard(
    expense: Expense,
    onCardClick: (Expense) -> Unit
) {
    val clickable = expense.kId != 1
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
            .then(
                if (clickable) Modifier.clickable { onCardClick(expense) } else Modifier
            )
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