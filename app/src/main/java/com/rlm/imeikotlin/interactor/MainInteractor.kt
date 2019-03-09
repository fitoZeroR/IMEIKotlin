package com.rlm.imeikotlin.interactor

import com.rlm.imeikotlin.repository.ws.IRetrofitWS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainInteractor @Inject constructor(iRetrofitWS: IRetrofitWS) : Interactor(iRetrofitWS) {
    fun descargaBoleta(tokenSesion: String) = iRetrofitWS.obtieneBoleta(tokenSesion)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

    fun enviarFoto(tokenSesion: String, foto: String) = iRetrofitWS.enviarFotografia(tokenSesion, foto)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}