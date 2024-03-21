package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot.SavingDepotDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.savingDepot.SavingDepotRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingDepotViewModel @Inject constructor(
    private val savingDepotRepository: SavingDepotRepository
) : ViewModel() {

    val savingDepot: LiveData<SavingDepot> = savingDepotRepository.getSavingDepot()

    fun changeSavingDepot(changeAmount: Float) {
        viewModelScope.launch {
            val currentDepot = savingDepotRepository.getSavingDepot().value?:return@launch
            val updatedAmount = currentDepot.sdAmount + changeAmount
            val updatedDepot = currentDepot.copy(sdAmount = updatedAmount)
            savingDepotRepository.update(updatedDepot)
        }
    }
}