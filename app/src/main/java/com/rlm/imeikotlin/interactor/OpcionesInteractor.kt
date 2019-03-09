package com.rlm.imeikotlin.interactor

import com.rlm.imeikotlin.repository.ws.IRetrofitWS
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OpcionesInteractor @Inject constructor(iRetrofitWS: IRetrofitWS) : Interactor(iRetrofitWS) {
    fun consultaListaOpciones() = iRetrofitWS.obtieneOpciones()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}