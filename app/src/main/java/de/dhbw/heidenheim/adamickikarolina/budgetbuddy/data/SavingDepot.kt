package de.dhbw.heidenheim.adamickikarolina.budgetbuddy.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="savingDepot")
data class SavingDepot(
    @ColumnInfo(name="sdAmount")
    val sdAmount: Float,
){
    @PrimaryKey(autoGenerate = true)
    var sdId: Int? = null
}
