package com.rlm.imeikotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class LoginResponse(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: Data,
    @SerializedName(MESSAGE)
    val message: String,
    @SerializedName(TRACE)
    val trace: String)