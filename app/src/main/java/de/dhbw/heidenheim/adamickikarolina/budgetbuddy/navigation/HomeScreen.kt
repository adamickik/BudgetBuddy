package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview
@Composable
fun HomeScreen(){
    SavingsCard()
}


@Composable
fun SavingsCard(){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Text(
            // TODO: change with variable from string page
            text = "DEIN SPARDEPOT",
            modifier = Modifier
                .padding(start = 10.dp,
                    top = 10.dp,
                    end = 10.dp,
                    bottom = 2.dp),
        )
        Text(
            // TODO: change with actual value and currency
            text = "1509,59" + " €",
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
            modifier = Modifier
                .padding(start = 10.dp,
                    top = 2.dp,
                    end = 10.dp,
                    bottom = 10.dp)
                .align(Alignment.CenterHorizontally),
        )
        Button(
            onClick = { onClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally),
        ) {

            // TODO: change with text string
            Text("ZUWEISEN")
        }
    }
}

fun onClick() {
    TODO("Not yet implemented")
}
