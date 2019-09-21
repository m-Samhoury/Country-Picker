package com.moustafa.countrypicker.repository

import com.moustafa.countrypicker.BuildConfig
import com.moustafa.countrypicker.models.Country
import retrofit2.Response

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

interface Repository {

    suspend fun fetchCountriesList(onError: (Exception) -> Unit): List<Country>?

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

    companion object {
        fun formatStaticMapUrl(
            mapLatLng: List<Double>?,
            width: Int? = null,
            height: Int? = null
        ): String? {
            if (mapLatLng?.size == 2) {
                var url = "https://image.maps.api.here.com/mia/1.6/mapview" +
                        "?app_id=${BuildConfig.MAP_APP_ID}" +
                        "&app_code=${BuildConfig.MAP_APP_CODE}" +
                        "&lat=${mapLatLng[0]}" +
                        "&lon=${mapLatLng[1]}" +
                        "&vt=0" +
                        "&z=7"
                if (width ?: 0 > 0 && height ?: 0 > 0) {
                    url = url.plus("&w=$width&h=$height")
                }
                return url
            }
            return null
        }
    }
}


sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}