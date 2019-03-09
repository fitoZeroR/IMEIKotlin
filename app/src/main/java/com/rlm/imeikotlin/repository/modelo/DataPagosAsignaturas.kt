package com.rlm.imeikotlin.repository.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class DataPagosAsignaturas(
    @SerializedName(PAGOS)
    val pagos: List<Pagos>,
    @SerializedName(PLAN)
    val plan: List<Plan>)