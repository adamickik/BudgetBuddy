package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ChartType
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel

@Composable
fun ChartCard(
    chartType: ChartType,
    chartViewModel : ChartViewModel
) {
    val savingsGoalName = chartViewModel.savingsGoalName.observeAsState("Default Name")
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp),
    ) {
        Column {

            val text = when (chartType) {
                ChartType.LineChart -> stringResource(R.string.analytics_savingsHistory_name,
                    savingsGoalName.value
                )
                ChartType.PieChart -> stringResource(R.string.analytics_expenses_name)
            }

            Text(
                text = text,
                modifier = Modifier
                    .padding(start = 15.dp, top = 15.dp, end = 15.dp),
            )

            when (chartType) {
                ChartType.LineChart -> LineChart(chartViewModel)
                ChartType.PieChart -> PaymentsPieChart(chartViewModel)
            }
        }
    }
}