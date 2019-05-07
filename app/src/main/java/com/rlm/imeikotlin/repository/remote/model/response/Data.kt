package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    @Json(name = TOKEN_SESION)
    val tokenSesion: String,
    @Json(name = ALUMNO)
    val alumno: Alumno?
)