package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.TippDao

class TippsViewModel(private val tippDao: TippDao) : ViewModel() {

    val randomTipp: LiveData<Tipp> = tippDao.getRandomTipp()
}

class TippsViewModelFactory(private val tippDao: TippDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TippsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TippsViewModel(tippDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}