package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class EstatusPago(
    @SerializedName(PAGO)
    val pago: String,
    @SerializedName(NOMBRE)
    val nombre: String,
    @SerializedName(ESTATUS)
    val estatus: String)