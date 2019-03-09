package com.rlm.imeikotlin.presenter

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.interactor.OpcionesInteractor
import com.rlm.imeikotlin.repository.modelo.*
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class OpcionesPresenter @Inject constructor(private val opcionesInteractor: OpcionesInteractor) : Presenter<OpcionesPresenter.View>() {
    override fun terminate() {
        super.terminate()
        setView(null)
    }

    // RXKOTLIN
    fun consultaListaOpciones() {
        getView()?.showLoading()

        val disposable = opcionesInteractor.consultaListaOpciones().subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(opciones));
                    getView()?.hideLoading()
                    getView()?.despliegaOpciones(it)
                }
            },
            onError = {
                getView()?.hideLoading()
                getView()?.showError(it.message)
            },
            onComplete = {  }
        )

        addDisposableObserver(disposable)
    }

    interface View : IContractUI, Presenter.View {
        fun despliegaOpciones(opciones: Opciones)
    }
}