package com.rlm.imeikotlin.interactor

import com.rlm.imeikotlin.repository.ws.IRetrofitWS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class EnviarInformacionInteractor @Inject constructor(iRetrofitWS: IRetrofitWS) : Interactor(iRetrofitWS) {
    fun enviarInformacion(nombre: String, telefono: String, correo: String, comentarios: String, interes: String)
            = iRetrofitWS.enviarInformacion(nombre, telefono, correo, comentarios, interes)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}