package com.rlm.imeikotlin.data.repository.strategy

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.rlm.imeikotlin.data.remote.api.Resource
import com.rlm.imeikotlin.data.remote.api.Resource.Status.ERROR
import com.rlm.imeikotlin.data.remote.api.Resource.Status.SUCCESS
import kotlinx.coroutines.Dispatchers

/**
 * The database serves as the single source of truth.
 * Therefore UI can receive data updates from database only.
 * Function notify UI about:
 * [Resource.Status.SUCCESS] - with data from database
 * [Resource.Status.ERROR] - if error has occurred from any source
 * [Resource.Status.LOADING]
 */
fun <T, A> resultLiveData(databaseQuery: () -> LiveData<T>,
                          networkCall: suspend () -> Resource<A>,
                          saveCallResult: suspend (A) -> Unit): LiveData<Resource<T>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading<T>())
            val source = databaseQuery.invoke().map {
                Resource.success(
                    it
                )
            }
            emitSource(source)

            val responseStatus = networkCall.invoke()
            if (responseStatus.status == SUCCESS) {
                saveCallResult(responseStatus.data!!)
            } else if (responseStatus.status == ERROR) {
                emit(Resource.error<T>(responseStatus.message!!))
                emitSource(source)
            }
        }