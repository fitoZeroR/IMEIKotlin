package com.rlm.imeikotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Planteles(
    @SerializedName(NOMBRE_PLANTEL)
    val nombre: String,
    @SerializedName(LATITUD)
    val latitud: String,
    @SerializedName(LONGITUD)
    val longitud: String)