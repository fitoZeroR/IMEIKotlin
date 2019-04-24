package com.rlm.imeikotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class DescargaBoletaResponse(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: DataDescargaBoleta,
    @SerializedName(TRACE)
    val trace: String,
    @SerializedName(MESSAGE)
    val message: String)