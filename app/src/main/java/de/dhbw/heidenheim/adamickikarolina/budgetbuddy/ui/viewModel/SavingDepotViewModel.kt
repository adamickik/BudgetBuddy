package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepot
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.SavingDepotDao
import kotlinx.coroutines.launch

class SavingDepotViewModel(private val savingDepotDao: SavingDepotDao) : ViewModel() {

    val savingDepot: LiveData<SavingDepot> = savingDepotDao.getAll()

    fun changeSavingDepot(changeAmount: Float) {
        viewModelScope.launch {
            val currentDepot = savingDepotDao.getAll().value?:return@launch
            val updatedAmount = currentDepot.sdAmount + changeAmount
            val updatedDepot = currentDepot.copy(sdAmount = updatedAmount)
            savingDepotDao.update(updatedDepot)
        }
    }
}

class SavingsDepotViewModelFactory(private val savingDepotDao: SavingDepotDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SavingDepotViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SavingDepotViewModel(savingDepotDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}