package com.rlm.imeikotlin.data.remote.model.request

data class LoginRequest(
    val usuario: String,
    val password: String
)