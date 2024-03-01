package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
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
fun SavingsCard(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                top = 15.dp,
                end=15.dp,
                bottom = 5.dp)
    ) {
        Text(
            text = stringResource(R.string.savings_account),
            modifier = Modifier
                .padding(
                    start = 10.dp,
                    top = 10.dp),
        )
        Text(
            // TODO: change with actual value and currency
            text = "1509,59" + stringResource(R.string.savings_currency),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .padding(
                    top = 10.dp,
                    bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
        )
        Button(
            onClick = { },
            modifier = Modifier
                .padding(
                    bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(stringResource(R.string.assign_button))
        }
    }
}