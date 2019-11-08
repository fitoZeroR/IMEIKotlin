package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class EnviarInformacionResponse(
    @field:Json(name = CODE)
    val code: Int,
    @field:Json(name = DATA)
    val data: String?,
    @field:Json(name = MESSAGE)
    val message: String,
    @field:Json(name = TRACE)
    val trace: String)