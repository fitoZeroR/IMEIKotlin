package com.rlm.imeikotlin.data.local.entity.embedded

import androidx.room.ColumnInfo

data class Plan(
    val idMateria: String?,
    val materia: String?,
    @ColumnInfo(name = COLUMNA_ESTATUS_PLAN)
    val estatus: String?) {

    companion object {
        const val COLUMNA_ESTATUS_PLAN= "Estatus_Plan"
    }
}