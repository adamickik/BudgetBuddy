package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.nativeCanvas

@Composable
fun AnalyticsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Analytics Screen", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))
            LineChart(targetValue = 70f, currentValue = 10f, goalName = "BENZ")
            Spacer(modifier = Modifier.height(40.dp))
            PieChartWithLegend()
        }
    }
}

@Composable
fun LineChart(targetValue: Float, currentValue: Float, goalName: String ) {
    // Beispielwerte
    val points = listOf(0f, 50f, 20f, 80f, currentValue)
    val maxValue = points.maxOrNull()?.coerceAtLeast(targetValue) ?: targetValue // Stellt sicher, dass die y-Achse beide, Ziel und aktuelle Werte, darstellen kann
    val widthModifier = Modifier.fillMaxWidth().height(200.dp).padding(16.dp)

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "$goalName Ziel", fontWeight = FontWeight.Bold)
        Canvas(modifier = widthModifier) {
            val width = size.width
            val height = size.height
            val stepX = width / (points.size - 1)
            val stepY = height / maxValue

            // Zeichne Achsen
            drawLine(Color.Black, start = Offset(0f, height), end = Offset(width, height), strokeWidth = 4f) // X-Achse
            drawLine(Color.Black, start = Offset(0f, 0f), end = Offset(0f, height), strokeWidth = 4f) // Y-Achse

            // Zeichne Punkte und Linien
            points.forEachIndexed { index, value ->
                if (index < points.size - 1) {
                    val x1 = stepX * index
                    val y1 = height - (value * stepY)
                    val x2 = stepX * (index + 1)
                    val y2 = height - (points[index + 1] * stepY)

                    drawLine(
                        Color.Blue,
                        start = Offset(x1, y1),
                        end = Offset(x2, y2),
                        strokeWidth = 4f
                    )
                }
            }

            // Zeichne Ziellinie und Zielwert
            val targetY = height - (targetValue * stepY)
            drawLine(
                color = Color.Red,
                start = Offset(0f, targetY),
                end = Offset(width, targetY),
                strokeWidth = 2f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
            )
            drawContext.canvas.nativeCanvas.drawText(
                "$targetValue €",
                width - 100f,
                targetY - 5f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.RED
                    textSize = 40f
                }
            )

            // Zeichne aktuellen Wert
            val currentY = height - (currentValue * stepY)
            drawContext.canvas.nativeCanvas.drawText(
                "$currentValue €",
                width - 100f,
                currentY - 5f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLUE
                    textSize = 40f
                }
            )
        }
        // Text unter dem Graphen, wie viel noch zum Ziel fehlt
        val amountLeft = targetValue - currentValue
        Text(text = "Noch ${amountLeft}€ bis zum Ziel")
    }
}


@Composable
fun PieChartWithLegend() {
    val slices = listOf(30f, 10f, 60f)
    val colors = listOf(Color.Red, Color.Green, Color.Blue)
    val descriptions = listOf("Miete", "Essen", "OF Abos")

    Row(verticalAlignment = Alignment.CenterVertically) {
        Canvas(modifier = Modifier
            .size(200.dp)
            .padding(16.dp)) {
            val radius = size.minDimension / 2
            var startAngle = 0f
            val total = slices.sum()

            slices.forEachIndexed { index, slice ->
                val angle = (slice / total) * 360f
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = angle,
                    useCenter = true,
                    topLeft = Offset((size.width - 2 * radius) / 2, (size.height - 2 * radius) / 2),
                    size = androidx.compose.ui.geometry.Size(radius * 2, radius * 2)
                )
                startAngle += angle
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            descriptions.forEachIndexed { index, desc ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(16.dp, 16.dp), contentAlignment = Alignment.Center) {
                        Canvas(modifier = Modifier.size(16.dp)) {
                            drawRect(color = colors[index])
                        }
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = desc)
                }
            }
        }
    }
}
