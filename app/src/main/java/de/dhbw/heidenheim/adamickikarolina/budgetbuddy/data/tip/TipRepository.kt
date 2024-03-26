package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data.tip

import javax.inject.Inject

class TipRepository @Inject constructor(
    private val tipDao: TipDao
) {
    fun insert(tip: Tip) {
        tipDao.insert(tip)
    }

    fun insertAsList(tipList: List<Tip>) {
        tipDao.insertAsList(tipList)
    }

    fun delete(tip: Tip) {
        tipDao.delete(tip)
    }

    fun getRandomTipp(): Tip? {
        return tipDao.getRandomTipp()
    }

    fun getTippCount(): Int {
        return tipDao.getCount()
    }
}