package com.devsoc.hrmaa.fitbit.objects

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {

    private val httpClient = OkHttpClient.Builder()
    private val client = httpClient.build()

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.fitbit.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}