package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ChartType
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts.ChartCard

@Preview
@Composable
fun AnalyticsScreen(){
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            ChartCard(chartType = ChartType.LineChart)
            ChartCard(chartType = ChartType.PieChart)
        }
    }
}
