package com.rlm.imeikotlin.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = PlantelEntity.TABLE_NAME_PLANTEL)
data class PlantelEntity(
    @ColumnInfo(name = COLUMNA_NOMBRE)
    val nombre: String,
    @ColumnInfo(name = COLUMNA_LATITUD)
    val latitud: String,
    @ColumnInfo(name = COLUMNA_LONGITUD)
    val longitud: String) {

    companion object {
        const val TABLE_NAME_PLANTEL = "Plantel"
        const val COLUMNA_NOMBRE = "Nombre"
        const val COLUMNA_LATITUD = "Latitud"
        const val COLUMNA_LONGITUD = "Longitud"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}