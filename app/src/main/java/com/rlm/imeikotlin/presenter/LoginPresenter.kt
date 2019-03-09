package com.rlm.imeikotlin.presenter

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.interactor.LoginInteractor
import com.rlm.imeikotlin.repository.modelo.Login
import com.rlm.imeikotlin.repository.modelo.PagosAsignaturas
import com.rlm.imeikotlin.repository.modelo.RecuperarPassword
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class LoginPresenter @Inject constructor(private val loginInteractor: LoginInteractor) : Presenter<LoginPresenter.View>() {
    override fun terminate() {
        super.terminate()
        setView(null)
    }

    fun recuperaPassword(matricula: String) {
        getView()?.showLoading()

        addDisposableObserver(loginInteractor.cambiaPassword(matricula).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(enviarInformacion));
                    getView()?.hideLoading()
                    getView()?.muestraRespuestaRecuperarPassword(it)
                }
            },
            onError = {
                getView()?.hideLoading()
                getView()?.showError(it.message)
            },
            onComplete = {  }
        ))
    }

    fun consultaListaAsignaturasPagos(tokenSesion: String) {
        //getView()?.showLoading();

        addDisposableObserver(loginInteractor.consultaListaAsignaturasPagos(tokenSesion).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(pagosAsignaturas));
                    getView()?.hideLoading()
                    getView()?.despliegaPagosAsignaturas(it)
                }
            },
            onError = {
                getView()?.hideLoading()
                getView()?.showError(it.message)
            },
            onComplete = {  }
        ))
    }

    fun autentificaUsuario(matricula: String, password: String) {
        getView()?.showLoading()

        addDisposableObserver(loginInteractor.loginUsuario(matricula, password).subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(login));
                    //getView()?.hideLoading()
                    getView()?.respuestaLogin(it)
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
        fun respuestaLogin(login: Login)
        fun despliegaPagosAsignaturas(pagosAsignaturas: PagosAsignaturas)
        fun muestraRespuestaRecuperarPassword(recuperarPassword: RecuperarPassword)
    }
}