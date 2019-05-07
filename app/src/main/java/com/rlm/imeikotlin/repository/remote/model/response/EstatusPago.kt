package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EstatusPago(
    @field:Json(name = PAGO)
    val pago: String?,
    @field:Json(name = NOMBRE)
    val nombre: String?,
    @field:Json(name = ESTATUS)
    val estatus: String?)