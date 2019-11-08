package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Alumno(
    @Json(name = ID_ALUMNO)
    val idAlumno: String?,
    @Json(name = NOMBRE)
    val nombre: String?,
    @Json(name = PATERNO)
    val paterno: String?,
    @Json(name = MATERNO)
    val materno: String?,
    @Json(name = CUATRIMESTRE)
    val cuatrimestre: String?,
    @Json(name = ID_LICENCIATURA)
    val idLicenciatura: String?,
    @Json(name = ID_PLANTEL)
    val idPlantel: String?,
    @Json(name = CURP)
    val curp: String?,
    @Json(name = TELEFONO)
    val telefono: String?,
    @Json(name = MATRICULA)
    val matricula: String?,
    @Json(name = NACIMIENTO)
    val nacimiento: String?,
    @Json(name = FOTO)
    val foto: String?,
    @Json(name = LICENCIATURA)
    val licenciatura: String?,
    @Json(name = PLANTEL)
    val plantel: String?)