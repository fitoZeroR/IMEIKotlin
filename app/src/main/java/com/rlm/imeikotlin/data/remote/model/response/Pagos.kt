package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Pagos(
    @Json(name = CUATRIMESTRE)
    val cuatrimestre: String,
    @Json(name = NOMBRE)
    val nombre: String,
    @Json(name = PAGOS)
    val estatusPagos: List<EstatusPago>)