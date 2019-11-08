package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PagosAsignaturasResponse(
    @Json(name = CODE)
    val code: Int,
    @Json(name = DATA)
    val data: DataPagosAsignaturas,
    @Json(name = TRACE)
    val trace: String,
    @Json(name = MESSAGE)
    val message: String)