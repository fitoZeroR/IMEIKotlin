package com.rlm.imeikotlin.data.remote.model.response

import com.rlm.imeikotlin.utils.*
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpcionesResponse(
    @Json(name = SOMOS)
    val somos: List<Grado>,
    @Json(name = KINDER)
    val kinder: List<Grado>,
    @Json(name = PRIMARIA)
    val primaria: List<Grado>,
    @Json(name = BACHILLERATO)
    val bachillerato: List<Grado>,
    @Json(name = lICENCIATURAS)
    val licenciaturas: List<Grado>,
    @Json(name = MAESTRIAS)
    val maestrias: List<Grado>,
    @Json(name = DOCTORADOS)
    val doctorados: List<Grado>,
    @Json(name = DIPLOMADOS)
    val diplomados: List<Grado>)