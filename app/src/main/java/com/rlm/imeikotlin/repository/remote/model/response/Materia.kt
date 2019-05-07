package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Materia(
    @field:Json(name = ID_MATERIA)
    val idMateria: String,
    @field:Json(name = MATERIA)
    val materia: String,
    @field:Json(name = ESTATUS)
    val estatus: String)