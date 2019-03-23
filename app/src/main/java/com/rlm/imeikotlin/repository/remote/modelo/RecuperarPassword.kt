package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class RecuperarPassword(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: List<String>,
    @SerializedName(TRACE)
    val trace: String,
    @SerializedName(MESSAGE)
    val message: String)