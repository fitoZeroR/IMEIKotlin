package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FotoResponse(
    @field:Json(name = CODE)
    val code: Int,
    @field:Json(name = DATA)
    val data: DataFoto,
    @field:Json(name = MESSAGE)
    val message: String)