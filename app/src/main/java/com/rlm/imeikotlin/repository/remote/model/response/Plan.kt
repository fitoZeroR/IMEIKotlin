package com.rlm.imeikotlin.repository.remote.model.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Plan(
    @SerializedName(ID_CUATRIMESTRE)
    val idCuatrimestre: String,
    @SerializedName(NOMBRE)
    val nombre: String,
    @SerializedName(MATERIAS)
    val materia: List<Materia>)