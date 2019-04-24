package com.rlm.imeikotlin.repository.remote.model.request

data class LoginRequest(
    val usuario: String,
    val password: String
)