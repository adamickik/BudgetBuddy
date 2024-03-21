package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippDao
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippRepository
import kotlinx.coroutines.launch
@HiltViewModel
class TippsViewModel(
    private val tippRepository: TippRepository
) : ViewModel() {
    private val _randomTipp = MutableLiveData<Tipp?>()
    val randomTipp: LiveData<Tipp?> = _randomTipp

    init {
        fetchRandomTipp()
    }

    fun fetchRandomTipp() {
        viewModelScope.launch {
            _randomTipp.value = tippRepository.getRandomTipp()
        }
    }
}