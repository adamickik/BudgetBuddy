package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippDao
import kotlinx.coroutines.launch

class TippsViewModel(private val tippDao: TippDao) : ViewModel() {
    private val _randomTipp = MutableLiveData<Tipp?>()
    val randomTipp: LiveData<Tipp?> = _randomTipp

    init {
        fetchRandomTipp()
    }

    fun fetchRandomTipp() {
        viewModelScope.launch {
            _randomTipp.value = tippDao.getRandomTipp()
        }
    }
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