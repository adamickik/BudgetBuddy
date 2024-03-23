package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tipp

import androidx.lifecycle.LiveData
import javax.inject.Inject

class TippRepository @Inject constructor(
    private val tippDao: TippDao
) {
    fun insert(tipp: Tipp) {
        tippDao.insert(tipp)
    }

    fun insertAsList(tippList: List<Tipp>) {
        tippDao.insertAsList(tippList)
    }

    fun delete(tipp: Tipp) {
        tippDao.delete(tipp)
    }

    fun getRandomTipp(): Tipp? {
        return tippDao.getRandomTipp()
    }

    fun getTippCount(): Int {
        return tippDao.getCount()
    }
}