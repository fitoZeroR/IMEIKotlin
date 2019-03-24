package com.rlm.imeikotlin.repository.remote.modelo.request

data class EnviarInformacionRequest(val nombre: String,
                                    val telefono: String,
                                    val correo: String,
                                    val comentarios: String,
                                    val interes: String)