package com.example.omy.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "http://api.exchangeratesapi.io/v1/"
object Service {


    val api: IExchangeApiService by lazy {

        return@lazy Retrofit.Builder()
            .baseUrl(BASE_URL!!)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(IExchangeApiService::class.java)
    }


}