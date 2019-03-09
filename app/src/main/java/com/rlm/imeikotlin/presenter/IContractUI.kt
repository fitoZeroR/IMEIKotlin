package com.rlm.imeikotlin.presenter

interface IContractUI {
    fun showLoading()
    fun hideLoading()
    fun showError(mensaje: String?)
}