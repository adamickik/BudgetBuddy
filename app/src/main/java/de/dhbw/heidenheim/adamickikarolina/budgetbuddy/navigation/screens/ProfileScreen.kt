package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.navigation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LiveData
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.R
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.expenses.ExpenseDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.profile.ProfileCard
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals.SavingGoalCardMinimal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.savingGoals.SavingGoalDialog
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.composables.templates.TextIconButton
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel.SavingsGoalViewModel

enum class DialogType {
    None, Payment, SavingGoal
}

@Composable
fun ProfileScreen(
){
    val savingGoalViewModel = hiltViewModel<SavingsGoalViewModel>()
    val savingGoalsLiveData: LiveData<List<SavingGoal>> = savingGoalViewModel.getSavingGoals()
    val savingGoals = savingGoalsLiveData.observeAsState(initial = emptyList()).value
    var currentDialog by remember { mutableStateOf(DialogType.None) }
    var selectedSavingGoal by remember { mutableStateOf<SavingGoal?>(null) }

    Column {
        ProfileCard()
        TextIconButton(
            stringResource(R.string.savingsGoals_name),
            stringResource(R.string.savingsGoalsButton_description),
            onIconClick = {currentDialog = DialogType.SavingGoal }
        )

        LazyColumn {
            items(savingGoals.drop(1)) { savingGoal ->
                SavingGoalCardMinimal(savingsGoal = savingGoal) {
                    selectedSavingGoal = savingGoal
                    currentDialog = DialogType.SavingGoal
                }
            }
        }
    }

    when(currentDialog){
        DialogType.Payment -> ExpenseDialog(
            showDialog = true,
            pageIndex = 0,
            onDismiss = { currentDialog = DialogType.None },
        )
        DialogType.SavingGoal -> SavingGoalDialog(
            showDialog = true,
            onDismiss = {
                currentDialog = DialogType.None
                selectedSavingGoal = null
            },
            editingSavingGoal = selectedSavingGoal
        )
        DialogType.None -> Unit
    }
}