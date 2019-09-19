package com.moustafa.countrypicker.repository.network

import com.moustafa.countrypicker.models.Country
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

interface RestCountriesService {

    @GET("all")
    suspend fun fetchAllCountries(): Response<List<Country>>
}
