package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.MAPA_PLANTELES
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class InformacionPlantelesResponse(
    @field:Json(name = MAPA_PLANTELES)
    val planteles: List<Planteles>
)