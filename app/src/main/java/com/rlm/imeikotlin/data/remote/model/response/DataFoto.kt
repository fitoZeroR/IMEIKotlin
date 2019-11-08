package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.FOTO
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DataFoto(
    @Json(name = FOTO)
    val foto: String)