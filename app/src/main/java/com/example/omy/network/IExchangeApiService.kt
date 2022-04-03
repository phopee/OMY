package com.example.omy.network

import com.example.omy.entities.ExchangeRate
import com.example.omy.entities.Symbols
import kotlinx.coroutines.Deferred
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.*

const val access_key_ = "98a9835ebe89afe57f488bcacf9b4e4c"
interface IExchangeApiService {


    @GET("/latest?access_key=98a9835ebe89afe57f488bcacf9b4e4c&base=EUR")
    suspend fun getRatesAsync(): Response<ExchangeRate>
    @GET("symbols")
    suspend fun getSymbols(@Url url: String): List<Symbols>
    @GET("/{date}?access_key=98a9835ebe89afe57f488bcacf9b4e4c")
    suspend fun getHistoricalRates(@Path("date") date :String,@Query("base") base: String,@Query("symbols") symbols: String) :Response<ExchangeRate>
}

