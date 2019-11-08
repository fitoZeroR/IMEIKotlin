package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Grado(
    @Json(name = TITULO)
    val titulo: String,
    @Json(name = DESCRIPCION)
    val descripcion: String?,
    @Json(name = DESCRIPCION_AVISO)
    val descripcionAviso: String?,
    @Json(name = PLANTELES)
    val planteles: String?
)