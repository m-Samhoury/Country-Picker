package com.moustafa.countrypicker.repository

import retrofit2.Response

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

interface Repository {

    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        onError: (Exception) -> Unit
    ): T? {

        val result: Result<T> = safeApiResult(call)
        var data: T? = null

        when (result) {
            is Result.Success ->
                data = result.data
            is Result.Error -> {
                onError(result.exception)
            }
        }


        return data

    }

    private suspend fun <T : Any> safeApiResult(
        call: suspend () -> Response<T>
    ): Result<T> {
        try {
            val response = call.invoke()
            if (response.isSuccessful) return Result.Success(response.body()!!)

            return Result.Error(java.lang.Exception())
        } catch (exception: Exception) {
            return Result.Error(exception)
        }
    }

}


sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}