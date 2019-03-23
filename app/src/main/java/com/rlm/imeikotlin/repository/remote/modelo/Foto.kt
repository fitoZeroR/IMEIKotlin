package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Foto(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: DataFoto,
    @SerializedName(MESSAGE)
    val message: String)