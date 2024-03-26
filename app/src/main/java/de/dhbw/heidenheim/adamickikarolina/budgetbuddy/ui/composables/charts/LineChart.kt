package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ChartViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun LineChart(
    savingGoal: SavingGoal,
) {
    val chartViewModel = hiltViewModel<ChartViewModel>()

    val currentValue by chartViewModel.getSumOfExpensesByAssignmentID(assignmentId = savingGoal.sgId!!).observeAsState(0)
    val points by chartViewModel.getCumulativeAmountsByAssignmentId(savingGoal.sgId!!).observeAsState(initial = emptyList())
    val maxValue by chartViewModel.getMaxCumulativeAmountByAssignmentId(savingGoal.sgId!!).observeAsState(initial=0f)

    // Colors needed by Canvas, cannot work with MaterialTheme
    val primaryColor = colorResource(id = R.color.primaryColor)
    val onSurfaceColor = colorResource(id = R.color.onSurface)
    val onSurfaceVariantColor = colorResource(id = R.color.onSurfaceVariant)

    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 15.dp, start = 15.dp, end = 15.dp, bottom = 10.dp),
    ){
        Column(
            modifier=Modifier.padding(15.dp)
        ){
            Text(
                text =  stringResource(R.string.analytics_savingsHistory_name, savingGoal.sgName),
            )
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(10.dp)
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

                val targetY = size.height - (savingGoal.sgGoalAmount * stepY)
                val formattedGoalAmount = NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(savingGoal.sgGoalAmount)
                val formattedCurrentAmount = NumberFormat.getCurrencyInstance(Locale("de", "DE")).format(currentValue)

                if(savingGoal.sgId!=1){
                    drawLine(
                        color = primaryColor,
                        start = Offset(0f, targetY),
                        end = Offset(size.width, targetY),
                        strokeWidth = 5f
                    )

                    drawContext.canvas.nativeCanvas.drawText(
                        formattedGoalAmount,
                        size.width - getTextWidth(formattedGoalAmount),
                        targetY - 5f,
                        Paint().apply {
                            color = Color.BLACK
                            textSize = 40f
                        }
                    )
                }

                val currentY = size.height - (currentValue.toFloat() * stepY)

                drawContext.canvas.nativeCanvas.drawText(
                    formattedCurrentAmount,
                    size.width - getTextWidth(formattedCurrentAmount),
                    currentY - 5f,

                    Paint().apply {
                        color = Color.BLACK
                        textSize = 40f
                    }
                )
            }
        }
    }
}

fun getTextWidth(text: String, fontSize: Float = 40f): Float {
    val paint = Paint()
    paint.textSize = fontSize
    return paint.measureText(text)
}

