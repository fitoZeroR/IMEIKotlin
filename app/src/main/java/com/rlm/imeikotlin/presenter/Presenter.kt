package com.rlm.imeikotlin.presenter

import android.content.Context
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class Presenter<T : Presenter.View> {
    private val compositeDisposable = CompositeDisposable()

    private var view: T? = null

    fun getView(): T? {
        return view
    }

    fun setView(view: T?) {
        this.view = view
    }

    open fun terminate() {
        dispose()
    }

    fun addDisposableObserver(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    private fun dispose() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    interface View {
        fun context(): Context
    }
}