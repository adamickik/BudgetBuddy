package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.ChartType
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts.ChartCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.AssignmentDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.DotsIndicator
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.SnappingLazyRow
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingDepotCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.ExpenseViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AnalyticsScreen(){
    val savingGoalsViewModel = hiltViewModel<SavingsGoalViewModel>()
    val savingsGoalsListSize = savingGoalsViewModel.getSavingGoalCount()
    val pagerState = rememberPagerState(pageCount = { savingsGoalsListSize})

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DotsIndicator(
            totalDots = savingsGoalsListSize,
            selectedIndex = pagerState.currentPage
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            // TODO get this into ViewModel
            ChartCard(chartType = ChartType.LineChart)
        }
        ChartCard(chartType = ChartType.PieChart)
    }
}
