package com.rlm.imeikotlin.data.remote.model.request

data class EnviarInformacionRequest(
    val nombre: String,
    val telefono: String,
    val correo: String,
    val comentarios: String,
    val interes: String
)