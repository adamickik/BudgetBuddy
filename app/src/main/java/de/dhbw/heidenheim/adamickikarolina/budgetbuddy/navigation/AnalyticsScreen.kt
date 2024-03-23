package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts.LineChart
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.charts.PaymentsPieChart
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general.DotsIndicator
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AnalyticsScreen(){
    val savingGoalsViewModel = hiltViewModel<SavingsGoalViewModel>()
    val savingGoalsListSize = savingGoalsViewModel.getSavingGoalCount()
    val savingGoals by savingGoalsViewModel.savingsGoals.observeAsState(emptyList())

    val pagerState = rememberPagerState(pageCount = { savingGoals.size })

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        DotsIndicator(
            totalDots = savingGoalsListSize,
            selectedIndex = pagerState.currentPage
        )
        HorizontalPager(
            state = pagerState
        ) { page ->
            LineChart(savingGoals[page])
        }
        PaymentsPieChart()
    }
}
