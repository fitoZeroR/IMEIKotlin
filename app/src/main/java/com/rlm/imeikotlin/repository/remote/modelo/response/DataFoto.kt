package com.rlm.imeikotlin.repository.remote.modelo.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.FOTO

data class DataFoto(
    @SerializedName(FOTO)
    val foto: String)