package com.rlm.imeikotlin.presenter

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.interactor.EnviarInformacionInteractor
import com.rlm.imeikotlin.repository.modelo.EnviarInformacion
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class EnviarInformacionPresenter @Inject constructor(private val enviarInformacionInteractor: EnviarInformacionInteractor) : Presenter<EnviarInformacionPresenter.View>() {
    override fun terminate() {
        super.terminate()
        setView(null)
    }

    // RXKOTLIN
    fun enviaInformacion(nombre: String, telefono: String, correo: String, comentarios: String, interes: String) {
        getView()?.showLoading()

        addDisposableObserver(enviarInformacionInteractor.enviarInformacion(nombre, telefono, correo, comentarios, interes).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(enviarInformacion));
                    getView()?.hideLoading()
                    getView()?.respuestaEnvioInformacion(it)
                }
            },
            onError = {
                getView()?.hideLoading()
                getView()?.showError(it.message)
            },
            onComplete = {  }
        ))
    }

    interface View : IContractUI, Presenter.View {
        fun respuestaEnvioInformacion(enviarInformacion: EnviarInformacion)
    }
}