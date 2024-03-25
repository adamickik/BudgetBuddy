package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
//import androidx.compose.runtime.launchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import androidx.hilt.navigation.compose.hiltViewModel
//import androidx.compose.runtime.livedata.collectAsState


import androidx.compose.runtime.livedata.observeAsState

//import androidx.compose.runtime.collectAsState
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.category.CategoryExpenseSummary

@Composable
fun PaymentsPieChart() {
    val chartViewModel: ChartViewModel = hiltViewModel()
    val categoryExpensesSummary by chartViewModel.categoryExpensesSummary.observeAsState(initial = emptyList())

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Ausgaben nach Kategorie",
                modifier = Modifier.padding(bottom = 16.dp)
            )

            PieChart(categoryExpensesSummary = categoryExpensesSummary)

            Legend(categoryExpensesSummary = categoryExpensesSummary)
        }
    }
}

@Composable
fun PieChart(categoryExpensesSummary: List<CategoryExpenseSummary>) {
    val total = categoryExpensesSummary.map { it.totalAmount }.fold(0f) { acc, e -> acc + e }
    Canvas(modifier = Modifier.size(250.dp).padding(8.dp)) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2
        var startAngle = -90f

        categoryExpensesSummary.forEach { summary ->
            val sweepAngle = (summary.totalAmount.toFloat() / total) * 360f
            val paintColor = Color((Math.random() * 0xffffff).toInt() or (0xff shl 24))
            drawArc(
                color = paintColor,
                startAngle = startAngle,
                sweepAngle = sweepAngle,
                useCenter = true,
                topLeft = Offset(center.x - radius, center.y - radius),
                size = Size(radius * 2, radius * 2)
            )
            startAngle += sweepAngle
        }
    }
}

@Composable
fun Legend(categoryExpensesSummary: List<CategoryExpenseSummary>) {
    Column {
        categoryExpensesSummary.forEach { summary ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(20.dp).padding(2.dp), contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(16.dp)) {
                        drawRect(color = Color((Math.random() * 0xffffff).toInt() or (0xff shl 24)))
                    }
                }
                Text(text = summary.categoryName + ": " + String.format("%.2f", summary.totalAmount), modifier = Modifier.padding(start = 5.dp))
            }
        }
    }
}
