package com.cafezin.calculadoraimc.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Calculo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "tipo") val tipo: String,
    @ColumnInfo(name = "resultado") val resultado: Double,
    @ColumnInfo(name = "data_de_medicao") val data: Date = Date()
)
