package com.elcomercio.mvvm_dagger_kotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rlm.imeikotlin.utils.VIEW_MODEL_NOT_FOUND
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Carlos Vargas on 20/12/17.
 */

@Singleton
class SampleViewModelFactory @Inject constructor(private val creators: Map<Class<out ViewModel>,
        @JvmSuppressWildcards Provider<ViewModel>>) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("$VIEW_MODEL_NOT_FOUND - $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }
}