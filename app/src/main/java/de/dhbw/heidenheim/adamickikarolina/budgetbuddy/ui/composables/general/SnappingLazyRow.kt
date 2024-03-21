package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.general

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingDepotCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savings.SavingsCard

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SnappingLazyRow(
    savingsGoals: List<SavingGoal>,
    savingDepot: SavingDepot,
    onAssignButtonClick: () -> Unit) {
    // PagerState for snap behaviour
    val savingsGoalsListSize = savingsGoals.size
    val pagerState = rememberPagerState(pageCount = { savingsGoalsListSize+1 })

    DotsIndicator(
        totalDots = savingsGoalsListSize+1,
        selectedIndex = pagerState.currentPage,
        selectedColor = MaterialTheme.colorScheme.onSurfaceVariant,
        unSelectedColor = MaterialTheme.colorScheme.outlineVariant,
    )

    HorizontalPager(
        state = pagerState
    ) { page ->
        when(page){
            0-> SavingDepotCard(
                savingDepot = savingDepot,
                onAssignButtonClick = onAssignButtonClick)
            else -> SavingsCard(savingsGoal = savingsGoals[page-1])
        }
    }
}

