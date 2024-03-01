package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ChartType
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.ChartCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R

@Preview
@Composable
fun AnalyticsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column {
            ChartCard(chartType = ChartType.LineChart)
            ChartCard(chartType = ChartType.PieChart)
        }
    }
}
