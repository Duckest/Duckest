package com.duckest.duckest.data

import com.duckest.duckest.data.Error as ErrorResult

sealed class NetworkResult<T>(
    val message: String? = null,
    val typeError: ErrorResult? = null,
    val data: T? = null
) {
    class Success<T>(data: T) : NetworkResult<T>(data = data)
    class Error<T>(message: String? = null, typeError: ErrorResult? = null, data: T? = null) : NetworkResult<T>(message, typeError, data)
    class Loading<T> : NetworkResult<T>()
}
