package com.rlm.imeikotlin.repository.remote.modelo.response

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Alumno(
    @SerializedName(ID_ALUMNO)
    val idAlumno: String?,
    @SerializedName(NOMBRE)
    val nombre: String?,
    @SerializedName(PATERNO)
    val paterno: String?,
    @SerializedName(MATERNO)
    val materno: String?,
    @SerializedName(CUATRIMESTRE)
    val cuatrimestre: String?,
    @SerializedName(ID_LICENCIATURA)
    val idLicenciatura: String?,
    @SerializedName(ID_PLANTEL)
    val idPlantel: String?,
    @SerializedName(CURP)
    val curp: String?,
    @SerializedName(TELEFONO)
    val telefono: String?,
    @SerializedName(MATRICULA)
    val matricula: String?,
    @SerializedName(NACIMIENTO)
    val nacimiento: String?,
    @SerializedName(FOTO)
    val foto: String?,
    @SerializedName(LICENCIATURA)
    val licenciatura: String?,
    @SerializedName(PLANTEL)
    val plantel: String?)