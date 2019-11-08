package com.rlm.imeikotlin.data.local.entity.embedded

import androidx.room.ColumnInfo

data class Pago(
    val pago: String?,
    val concepto: String?,
    @ColumnInfo(name = COLUMNA_ESTATUS_PAGO)
    val estatus: String?) {

    companion object {
        const val COLUMNA_ESTATUS_PAGO = "Estatus_Pago"
    }
}