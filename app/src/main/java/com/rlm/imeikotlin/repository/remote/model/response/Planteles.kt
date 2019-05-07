package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Planteles(
    @Json(name = NOMBRE_PLANTEL)
    val nombre: String,
    @Json(name = LATITUD)
    val latitud: String,
    @Json(name = LONGITUD)
    val longitud: String)