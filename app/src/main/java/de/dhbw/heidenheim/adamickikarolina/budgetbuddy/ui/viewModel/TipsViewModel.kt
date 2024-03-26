package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip.Tip
import de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip.TipRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TipsViewModel @Inject constructor(
    private val tipRepository: TipRepository
) : ViewModel() {
    private val _randomTip = MutableLiveData<Tip?>()
    val randomTip: LiveData<Tip?> = _randomTip

    init {
        fetchRandomTipp()
    }

    fun fetchRandomTipp() {
        viewModelScope.launch {
            _randomTip.value = tipRepository.getRandomTipp()
        }
    }

    fun getTipCount(): Int {
        return tipRepository.getTippCount()
    }

    fun insertAsList(tips: List<Tip>) {
        tipRepository.insertAsList(tips)
    }
}