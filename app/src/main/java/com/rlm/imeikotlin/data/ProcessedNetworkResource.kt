package com.rlm.imeikotlin.data

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rlm.imeikotlin.data.remote.api.ApiResponse
import com.rlm.imeikotlin.utils.ERROR_SERVICE_RESPONSE

abstract class ProcessedNetworkResource<R, C> {
    private val result: MediatorLiveData<Resource<C>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    private fun fetchFromNetwork() {
        val apiResponseLiveData = createCall()
        setValue(Resource.loading(null))
        result.addSource(apiResponseLiveData) {
            result.removeSource(apiResponseLiveData)
            if (it != null) {
                if (it.isSuccessful()) {
                    setValue(Resource.success(processResponse(it.body!!)))
                } else {
                    setValue(Resource.error(it.errorMessage!!, null))
                }
            } else {
                setValue(Resource.error(ERROR_SERVICE_RESPONSE, null))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<C>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData(): LiveData<Resource<C>> = result

    @NonNull
    @MainThread
    abstract fun createCall(): LiveData<ApiResponse<R>>

    @WorkerThread
    abstract fun processResponse(response: R): C?
}