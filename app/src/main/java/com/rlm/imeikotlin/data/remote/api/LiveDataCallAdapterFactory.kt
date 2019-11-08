package com.rlm.imeikotlin.data.remote.api

import androidx.lifecycle.LiveData
import com.rlm.imeikotlin.utils.*
import retrofit2.CallAdapter
import retrofit2.CallAdapter.Factory
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * A Retrofit Adapter Factory that handle an adapter the {@link LiveDataCallAdapter}.
 *
 * @author Carlos Vargas on 2/19/18
 * @version 1.0.0
 * @see LiveData
 * @see ApiResponse
 * @see ParameterizedType
 * @see CallAdapter.Factory#getParameterUpperBound(int, ParameterizedType)
 * @see CallAdapter.Factory#getRawType(Type)
 * @see IllegalArgumentException
 * @see LiveDataCallAdapter
 * @since 1.0.0
 */

class LiveDataCallAdapterFactory : Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (getRawType(returnType) != LiveData::class.java) {
            return null
        }
        val observableType = getParameterUpperBound(0, returnType as ParameterizedType)
        val rawObservableType = getRawType(observableType)
        if (rawObservableType != ApiResponse::class.java) {
            throw IllegalArgumentException(TYPE_MUST_BE_A_RESOURCE)
        }
        if (observableType !is ParameterizedType) {
            throw IllegalArgumentException(RESOURCE_MUST_BE_PARAMETERIZED)
        }
        val bodyType = getParameterUpperBound(0, observableType)
        return LiveDataCallAdapter<Any>(bodyType)
    }
}