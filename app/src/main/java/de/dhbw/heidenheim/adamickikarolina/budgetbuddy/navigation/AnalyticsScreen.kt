package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ChartType
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts.ChartCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel

@Preview
@Composable
fun AnalyticsScreen(chartViewModel: ChartViewModel) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            ChartCard(chartType = ChartType.LineChart, chartViewModel)
            ChartCard(chartType = ChartType.PieChart, chartViewModel)
        }
    }
}
