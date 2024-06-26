package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.charts

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.AnalyticsViewModel

@Composable
fun PaymentsPieChart(
) {
    val analyticsViewModel = hiltViewModel<AnalyticsViewModel>()

    val slices by analyticsViewModel.sumCategories.observeAsState(initial = listOf())
    val totalExpenses by analyticsViewModel.sumNegative.observeAsState(0f)

    // Colors needed by Canvas, cannot work with MaterialTheme
    val colorPie1 = colorResource(id = R.color.col1)
    val colorPie2 = colorResource(id = R.color.col2)
    val colorPie3 = colorResource(id = R.color.col3)
    val colorPie4 = colorResource(id = R.color.col4)
    val colorPie5 = colorResource(id = R.color.col5)
    val colors = listOf(colorPie1, colorPie3, colorPie2,colorPie4,colorPie5)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp),
    ){
        Text(
            text =  stringResource(R.string.analytics_expenses_name),
            modifier = Modifier
                .padding(start = 15.dp, top = 15.dp, end = 15.dp),
        )
        Row(
            modifier= Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Canvas(
                modifier = Modifier
                    .size(200.dp)
                    .padding(16.dp)
            ) {
                val radius = size.minDimension / 2
                var startAngle = 0f

                slices.forEachIndexed { index, slice ->
                    val angle = (slice.sum / totalExpenses) * 360f
                    drawArc(
                        color = colors[index],
                        startAngle = startAngle,
                        sweepAngle = angle,
                        useCenter = true,
                        topLeft = Offset((size.width - 2 * radius) / 2, (size.height - 2 * radius) / 2),
                        size = Size(radius * 2, radius * 2)
                    )
                    startAngle += angle
                }
            }
            Column {
                slices.forEachIndexed { index, slice ->
                    val categoryName by analyticsViewModel.getCategoryNameById(slice.categoryId).observeAsState("")

                    Row(
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Box(
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(
                                modifier = Modifier.size(16.dp)
                            ) {
                                drawRect(color = colors[index])
                            }
                        }
                        Text(
                            text = categoryName,
                            modifier = Modifier.padding(start = 5.dp)
                        )
                    }
                }
            }
        }
    }
}