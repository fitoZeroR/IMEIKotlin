package com.rlm.imeikotlin.repository.remote.modelo.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.MAPA_PLANTELES

data class InformacionPlantelesResponse(
    @SerializedName(MAPA_PLANTELES)
    val planteles: List<Planteles>)