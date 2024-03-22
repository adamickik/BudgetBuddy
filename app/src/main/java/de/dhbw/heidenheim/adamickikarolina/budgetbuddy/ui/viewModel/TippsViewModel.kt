package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.Tipp
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp.TippRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TippsViewModel @Inject constructor(
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

    fun getTippCount(): Int {
        return tippRepository.getTippCount()
    }

    fun insertAsList(tipps: List<Tipp>) {
        tippRepository.insertAsList(tipps)
    }
}