package com.rlm.imeikotlin.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rlm.imeikotlin.data.Resource.Status.ERROR
import com.rlm.imeikotlin.data.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

fun <T, A> resultLiveDataPost(networkCall: suspend () -> Resource<A>,
                              returnData: suspend (A) -> LiveData<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        /*val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            val source: LiveData<Resource<T>> = responseStatus.data
            emitSource(responseStatus.)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }*/

        val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            val source = returnData.invoke(responseStatus.data!!).map { Resource.success(it) }
            emitSource(source)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }
    }

/*fun <T> resultLiveDataPost(networkCall: suspend () -> LiveData<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())

        /*val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            val source: LiveData<Resource<T>> = responseStatus.data
            emitSource(responseStatus.)
        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
        }*/

        val responseStatus = networkCall.invoke().map { Resource.success(it) }
        if (responseStatus.value!!.status == SUCCESS) {
            emitSource(responseStatus)
        } else if (responseStatus.value!!.status == ERROR) {
            emit(Resource.error(responseStatus.value!!.message!!))
        }
    }*/