package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

@Preview
@Composable
fun PaymentCard(){
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
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            Text(
                // TODO: Change with actual payment title
                text = "MIETE",

                modifier = Modifier
                    .padding(start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp)
                    .weight(1f),
            )
            Text(
                // TODO: change with actual payment
                text = "1509,59" + stringResource(R.string.savings_currency),
                modifier = Modifier
                    .padding(start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp),
            )
        }
    }
}