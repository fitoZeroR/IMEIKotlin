package com.rlm.imeikotlin.repository.remote.modelo.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.BOLETA_URL

data class DataDescargaBoleta(
    @SerializedName(BOLETA_URL)
    val boletaUrl: String)