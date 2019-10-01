package com.rlm.imeikotlin.repository

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.rlm.imeikotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.utils.ERROR_SERVICE_RESPONSE
import com.rlm.imeikotlin.utils.Resource

abstract class NetworkResource<R> {
    private val result: MediatorLiveData<Resource<R>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        fetchFromNetwork()
    }

    /**
     * Inside this method you can define your own logic.
     */
    private fun fetchFromNetwork() {
        val apiResponseLiveData = createCall()
        setValue(Resource.loading(null))
        result.addSource(apiResponseLiveData) {
            result.removeSource(apiResponseLiveData)
            if (it != null) {
                if (it.isSuccessful()) {
                    //Logger.json(Gson().toJson(it.body))
                    setValue(Resource.success(it.body))
                } else {
                    setValue(Resource.error(it.errorMessage!!, null))
                }
            } else {
                setValue(Resource.error(ERROR_SERVICE_RESPONSE, null))
            }
        }
    }

    @MainThread
    private fun setValue(newValue: Resource<R>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    fun asLiveData(): LiveData<Resource<R>> = result

    @NonNull
    @MainThread
    abstract fun createCall(): LiveData<ApiResponse<R>>
}