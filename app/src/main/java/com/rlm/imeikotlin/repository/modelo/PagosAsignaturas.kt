package com.rlm.imeikotlin.repository.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class PagosAsignaturas(
    @SerializedName(CODE)
    val code: Int,
    @SerializedName(DATA)
    val data: DataPagosAsignaturas,
    @SerializedName(TRACE)
    val trace: String,
    @SerializedName(MESSAGE)
    val message: String)