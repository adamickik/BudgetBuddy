package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnappingLazyRow(savingsGoals: List<SavingGoal>) {
    // PagerState for snap behaviour
    val savingsGoalsListSize = savingsGoals.size
    val pagerState = rememberPagerState(pageCount = { savingsGoalsListSize })

    HorizontalPager(
        state = pagerState
    ) { page ->
            SavingsCard(savingsGoal = savingsGoals[page])
    }
}