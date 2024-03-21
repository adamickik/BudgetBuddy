package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingDepotCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnappingLazyRow(savingsGoals: List<SavingGoal>, savingDepot: SavingDepot) {
    // PagerState for snap behaviour
    val savingsGoalsListSize = savingsGoals.size
    val pagerState = rememberPagerState(pageCount = { savingsGoalsListSize+1 })

    HorizontalPager(
        state = pagerState
    ) { page ->
        when(page){
            0-> SavingDepotCard(savingDepot = savingDepot)
            else -> SavingsCard(savingsGoal = savingsGoals[page-1])
        }
    }
}