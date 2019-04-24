package com.rlm.imeikotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Pagos(
    @SerializedName(CUATRIMESTRE)
    val cuatrimestre: String,
    @SerializedName(NOMBRE)
    val nombre: String,
    @SerializedName(PAGOS)
    val estatusPagos: List<EstatusPago>)