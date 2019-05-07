package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.BOLETA_URL
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataDescargaBoleta(
    @Json(name = BOLETA_URL)
    val boletaUrl: String)