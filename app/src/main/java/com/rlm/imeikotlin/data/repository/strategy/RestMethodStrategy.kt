package com.rlm.imeikotlin.data.repository.strategy

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rlm.imeikotlin.data.remote.api.Resource
import com.rlm.imeikotlin.data.remote.api.Resource.Status.ERROR
import com.rlm.imeikotlin.data.remote.api.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveDataRest(networkCall: suspend () -> Resource<A>,
                              returnData: suspend (A) -> LiveData<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            val source = returnData.invoke(responseStatus.data!!).map {
                Resource.success(
                    it
                )
            }
            emitSource(source)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }

fun <T> returnLiveDataResponse(data: T): LiveData<T> {
    val getDataLiveData: MutableLiveData<T> = MutableLiveData()
    getDataLiveData.value = data
    return getDataLiveData
}