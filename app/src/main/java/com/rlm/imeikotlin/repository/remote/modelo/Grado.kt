package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Grado(
    @SerializedName(TITULO)
    val titulo: String,
    @SerializedName(DESCRIPCION)
    val descripcion: String,
    @SerializedName(DESCRIPCION_AVISO)
    val descripcionAviso: String,
    @SerializedName(PLANTELES)
    val planteles: String?
)