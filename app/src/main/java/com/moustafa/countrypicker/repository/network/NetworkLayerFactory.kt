package com.moustafa.countrypicker.repository.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.squareup.moshi.Moshi
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

/**
 * This helper class provide methods to setup retrofit in the application
 *
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

object NetworkLayerFactory {
    private const val TIME_OUTS: Long = 40L

    fun makeHttpClient(context: Context): OkHttpClient =
        makeHttpClientBuilder(context)
            .addInterceptor(ChuckerInterceptor(context))
            .build()

    fun makeHttpClientBuilder(context: Context): OkHttpClient.Builder =
        OkHttpClient.Builder()
            .connectTimeout(TIME_OUTS, TimeUnit.SECONDS)
            .readTimeout(TIME_OUTS, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUTS, TimeUnit.SECONDS)
            .certificatePinner(CertificatePinner.DEFAULT)
            .retryOnConnectionFailure(false)


    fun createMoshiInstance() = Moshi.Builder()
        .build()
        .apply {

        }


    inline fun <reified T> makeServiceFactory(
        retrofit: Retrofit
    ): T = retrofit.create(T::class.java)


    fun makeRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(createMoshiInstance()))
            .build()
        return retrofit
    }

}
