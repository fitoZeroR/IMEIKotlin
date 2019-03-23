package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.MAPA_PLANTELES

data class InformacionPlanteles(
    @SerializedName(MAPA_PLANTELES)
    val planteles: List<Planteles>)