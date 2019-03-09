package com.rlm.imeikotlin.presenter

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.interactor.MainInteractor
import com.rlm.imeikotlin.repository.modelo.DescargaBoleta
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class MainPresenter @Inject constructor(private val mainInteractor: MainInteractor) : Presenter<MainPresenter.View>() {
    fun descargaBoleta(tokenSesion: String) {
        getView()?.showLoading()

        addDisposableObserver(mainInteractor.descargaBoleta(tokenSesion).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(it));
                    getView()?.hideLoading()
                    getView()?.compartirBoleta(it)
                }
            },
            onError = {
                getView()?.hideLoading()
                getView()?.showError(it.message)
            },
            onComplete = {  }
        ))
    }

    fun enviarImagen(tokenSesion: String, imagen: String) {
        getView()?.showLoading()

        addDisposableObserver(mainInteractor.enviarFoto(tokenSesion, imagen).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(it));
                    getView()?.hideLoading()
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
        fun compartirBoleta(descargaBoleta: DescargaBoleta)
    }
}