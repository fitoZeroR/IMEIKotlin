package com.rlm.imeikotlin.data.remote.api

import com.orhanobut.logger.Logger
import com.rlm.imeikotlin.utils.TAG
import retrofit2.Response

/**
 * Abstract Base Data source class with error handling
 */
abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) return Resource.success(body)
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {
        Logger.i(TAG, message)
        return Resource.error("Network call has failed for a following reason: $message")
    }
}