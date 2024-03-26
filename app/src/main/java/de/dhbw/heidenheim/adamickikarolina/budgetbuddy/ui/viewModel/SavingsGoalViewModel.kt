package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoal
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingGoal.SavingGoalRepository
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SavingsGoalViewModel @Inject constructor(
    private val savingGoalRepository: SavingGoalRepository
) : ViewModel() {
    val savingsGoals: LiveData<List<SavingGoal>> = savingGoalRepository.getAllSavingGoals()
    val savingsGoalsCount : LiveData<Int> = savingGoalRepository.getSavingGoalCount()

    fun insertSavingsGoal(title: String, value: String, date: String) {
        val expenseValue = convertGermanCurrencyStringToFloat(value)
        val newSavingGoal = SavingGoal(title, expenseValue, date)

        savingGoalRepository.insert(newSavingGoal)
    }

    fun insertAsList(savingGoals: List<SavingGoal>) {
        savingGoalRepository.insertAsList(savingGoals)
    }

    fun insertAsListAndGetIds(savingGoals: List<SavingGoal>): List<Long> {
        return savingGoalRepository.insertAsListAndGetIds(savingGoals)
    }

    fun editSavingGoal(savingGoal: SavingGoal) {
        savingGoalRepository.update(savingGoal)
    }

    fun deleteSavingGoal(savingGoal: SavingGoal) {
        savingGoalRepository.delete(savingGoal)
    }

    fun isValidTitle(savingGoalTitle: String): Boolean {
        return savingGoalTitle.matches(Regex("^[a-zA-Z]{2,16}\$"))
    }

    fun isValidValue(savingGoalValue: String): Boolean {
        return savingGoalValue.matches(Regex("^(?!-)\\+?(\\d{1,3}(\\.\\d{3})?|(\\d{1,6}))(,\\d{2})?\$"))
    }

    fun isValidDueDate(savingGoalDueDate: String): Boolean {
        return savingGoalDueDate.matches(Regex("^(0[1-9]|[12][0-9]|3[01])\\.(0[1-9]|1[012])\\.((19|20)\\d\\d)\$"))
    }

    fun convertGermanCurrencyStringToFloat(currencyString: String): Float {
        val normalizedString = currencyString
            .replace(".", "")
            .replace(",", ".")
        return normalizedString.toFloat()
    }

    companion object {
        fun floatToGermanCurrencyString(it: Float):String {
            return String.format(Locale.GERMAN, "%.2f", it)
        }
    }
}