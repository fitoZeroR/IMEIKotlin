package com.rlm.imeikotlin.repository.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Data(
    @SerializedName(TOKEN_SESION)
    val tokenSesion: String,
    @SerializedName(ALUMNO)
    val alumno: Alumno)