package com.rlm.imeikotlin.repository.remote.modelo.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Materia(
    @SerializedName(ID_MATERIA)
    val idMateria: String,
    @SerializedName(MATERIA)
    val materia: String,
    @SerializedName(ESTATUS)
    val estatus: String)