package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun BudgetBuddyTheme(
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = LightThemeColors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )

}