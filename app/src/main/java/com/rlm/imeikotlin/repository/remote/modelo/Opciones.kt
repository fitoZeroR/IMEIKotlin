package com.rlm.imeikotlin.repository.remote.modelo

import com.google.gson.annotations.SerializedName
import com.rlm.imeikotlin.utils.*

data class Opciones(
    @SerializedName(SOMOS)
    val somos: List<Grado>,
    @SerializedName(KINDER)
    val kinder: List<Grado>,
    @SerializedName(PRIMARIA)
    val primaria: List<Grado>,
    @SerializedName(BACHILLERATO)
    val bachillerato: List<Grado>,
    @SerializedName(lICENCIATURAS)
    val licenciaturas: List<Grado>,
    @SerializedName(MAESTRIAS)
    val maestrias: List<Grado>,
    @SerializedName(DOCTORADOS)
    val doctorados: List<Grado>,
    @SerializedName(DIPLOMADOS)
    val diplomados: List<Grado>)