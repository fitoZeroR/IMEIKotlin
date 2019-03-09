package com.rlm.imeikotlin.interactor

import com.rlm.imeikotlin.repository.ws.IRetrofitWS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginInteractor @Inject constructor(iRetrofitWS: IRetrofitWS) : Interactor(iRetrofitWS) {
    fun cambiaPassword(matricula: String) = iRetrofitWS.recuperaPassword(matricula)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun loginUsuario(matricula: String, password: String) = iRetrofitWS.autenticarUsuario(matricula, password)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun consultaListaAsignaturasPagos(tokenSesion: String) = iRetrofitWS.obtieneAsignaturasPagos(tokenSesion)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}