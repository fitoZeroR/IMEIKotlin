package com.rlm.imeikotlin.repository

import androidx.annotation.MainThread
import androidx.annotation.NonNull
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.elcomercio.mvvm_dagger_kotlin.repository.remote.api.ApiResponse
import com.rlm.imeikotlin.utils.AppExecutors
import com.rlm.imeikotlin.utils.ERROR_SERVICE_RESPONSE
import com.rlm.imeikotlin.utils.Resource

abstract class DetailNetworkResource<R, T>(private val appExecutors: AppExecutors) {

    private val result: MediatorLiveData<Resource<R>> = MediatorLiveData()

    init {
        result.value = Resource.loading(null)
        val dbSource: LiveData<R> = loadFromDb()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) { //From Network resource
                fetchFromNetwork(dbSource)
            } else { //From Database
                result.addSource(dbSource) { newData ->
                    setValue(Resource.success(newData))
                }
            }
        }
    }

    private fun setValue(newValue: Resource<R>) {
        if (result.value != newValue) {
            result.value = newValue
        }
    }

    private fun fetchFromNetwork(dbSource: LiveData<R>) {
        val apiResponseLiveData = createCall()
        //result.addSource(dbSource) { newData -> setValue(Resource.loading(newData)) }
        result.addSource(apiResponseLiveData) { apiResponse ->
            result.removeSource(apiResponseLiveData)
            //result.removeSource(dbSource)

            if (apiResponse != null) {
                if (apiResponse.isSuccessful()) {
                    appExecutors.diskIO.execute {
                        saveCallResult(processResponse(apiResponse)!!)
                        appExecutors.mainThread.execute {
                            result.addSource(loadFromDb()) { newData ->
                                setValue(Resource.success(newData))
                            }
                        }
                    }
                } else {
                    result.addSource(dbSource) { newData -> setValue(Resource.error(apiResponse.errorMessage!!, newData)) }
                }
            } else {
                setValue(Resource.error(ERROR_SERVICE_RESPONSE, null))
            }
        }
    }

    fun asLiveData(): LiveData<Resource<R>> = result

    @WorkerThread
    private fun processResponse(response: ApiResponse<T>) = response.body

    @WorkerThread
    abstract fun saveCallResult(item: T)

    @MainThread
    abstract fun shouldFetch(data: R?): Boolean

    @NonNull
    @MainThread
    abstract fun loadFromDb(): LiveData<R>

    @NonNull
    @MainThread
    abstract fun createCall(): LiveData<ApiResponse<T>>
}