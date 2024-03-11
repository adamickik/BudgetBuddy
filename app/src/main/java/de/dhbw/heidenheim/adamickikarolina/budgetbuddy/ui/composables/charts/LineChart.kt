package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel

@Composable
fun LineChart(chartViewModel: ChartViewModel) {
    val targetValue by chartViewModel.targetValue.observeAsState(0f)
    val currentValue by chartViewModel.currentValue.observeAsState(0f)
    val points by chartViewModel.points.observeAsState(listOf())
    val maxValue by chartViewModel.maxValue.observeAsState(0f)

    val primaryColor = colorResource(id = R.color.primaryColor)
    val onSurfaceColor = colorResource(id = R.color.onSurface)
    val onSurfaceVariantColor = colorResource(id = R.color.onSurfaceVariant)

    Column(
        modifier=Modifier.padding(15.dp)
    ){
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {

            val stepX = size.width / (points.size - 1)
            val stepY = size.height / (maxValue * 1.1f)

            drawLine(
                color = onSurfaceColor,
                start = Offset(0f, size.height),
                end = Offset(size.width, size.height),
                strokeWidth = 5f)
            drawLine(
                color = onSurfaceColor,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = 5f)


            points.forEachIndexed { index, value ->
                if (index < points.size - 1) {
                    val x1 = stepX * index
                    val y1 = size.height - (value * stepY)
                    val x2 = stepX * (index + 1)
                    val y2 = size.height - (points[index + 1] * stepY)

                    drawLine(
                        color = onSurfaceVariantColor,
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = 5f)
                }
            }

            val targetY = size.height - (targetValue * stepY)
            drawLine(
                color = primaryColor,
                start = Offset(0f, targetY),
                end = Offset(size.width, targetY),
                strokeWidth = 5f
            )

            drawContext.canvas.nativeCanvas.drawText(
                "$targetValue €",
                size.width - 180f,
                targetY - 5f,
                Paint().apply {
                    color = Color.BLACK
                    textSize = 50f
                }
            )

            val currentY = size.height - (currentValue * stepY)
            drawContext.canvas.nativeCanvas.drawText(
                "$currentValue €",
                size.width - 150f,
                currentY - 5f,
                Paint().apply {
                    color = Color.BLACK
                    textSize = 50f
                }
            )
        }
    }
}
