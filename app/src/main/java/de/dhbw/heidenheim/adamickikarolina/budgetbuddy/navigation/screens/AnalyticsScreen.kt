package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.model.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.charts.LineChart
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.charts.PaymentsPieChart
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.view.composables.templates.DotsIndicator
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.viewModel.AnalyticsViewModel

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun AnalyticsScreen(){
    val analyticsViewModel = hiltViewModel<AnalyticsViewModel>()
    val savingGoals by analyticsViewModel.getAllSavingGoals().observeAsState(emptyList<SavingGoal>())
    val savingGoalsListSize by analyticsViewModel.savingsGoalsCount.observeAsState(0)

    val pagerState = rememberPagerState( pageCount = { savingGoals.size})

    LaunchedEffect(savingGoals.size, pagerState.currentPage) {
        if (pagerState.currentPage >= savingGoalsListSize) {
            pagerState.scrollToPage(0)
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            DotsIndicator(
                totalDots = savingGoalsListSize,
                selectedIndex = pagerState.currentPage
            )
        }
        item {
            HorizontalPager(
                state = pagerState
            ) { page ->
                savingGoals.getOrNull(page)?.let { savingGoal ->
                    LineChart(savingGoal)
                }
            }
        }
        item {
            PaymentsPieChart()
        }
    }
}
