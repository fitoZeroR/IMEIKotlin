package com.rlm.imeikotlin.presenter

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.interactor.PlantelInteractor
import com.rlm.imeikotlin.repository.modelo.InformacionPlanteles
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class PlantelPresenter @Inject constructor(private val plantelInteractor: PlantelInteractor) : Presenter<PlantelPresenter.View>() {
    // RXKOTLIN
    fun consultaListaPlanteles() {
        getView()?.showLoading()

        addDisposableObserver(plantelInteractor.consultaListaPlanteles().subscribeBy(
            onNext = {
                if (it == null)
                    getView()?.showError(null)
                else {
                    Logger.json(Gson().toJson(it))
                    //Log.i("RLM", new Gson().toJson(it));
                    getView()?.hideLoading()
                    getView()?.despliegaPlanteles(it)
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
        fun despliegaPlanteles(informacionPlanteles: InformacionPlanteles)
    }
}