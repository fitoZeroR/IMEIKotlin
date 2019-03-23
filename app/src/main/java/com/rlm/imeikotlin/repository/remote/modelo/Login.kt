package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Login(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: Data,
    @SerializedName(MESSAGE)
    val message: String,
    @SerializedName(TRACE)
    val trace: String)