package com.rlm.imeikotlin.repository.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RecuperarPasswordResponse(
    @field:Json(name = CODE)
    val code: Int,
    @field:Json(name = DATA)
    val data: List<String>,
    @field:Json(name = TRACE)
    val trace: String,
    @field:Json(name = MESSAGE)
    val message: String)