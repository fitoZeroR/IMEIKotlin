package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Plan(
    @field:Json(name = ID_CUATRIMESTRE)
    val idCuatrimestre: String,
    @field:Json(name = NOMBRE)
    val nombre: String,
    @field:Json(name = MATERIAS)
    val materia: List<Materia>)