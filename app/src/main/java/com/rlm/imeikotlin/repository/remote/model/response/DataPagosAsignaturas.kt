package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataPagosAsignaturas(
    @field:Json(name = PAGOS)
    val pagos: List<Pagos>,
    @field:Json(name = PLAN)
    val plan: List<Plan>)