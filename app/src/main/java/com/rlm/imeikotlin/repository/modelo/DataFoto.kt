package com.rlm.imeikotlin.repository.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.FOTO

data class DataFoto(
    @SerializedName(FOTO)
    val foto: String)