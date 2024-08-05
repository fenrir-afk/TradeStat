package com.example.data.news.api

import com.example.domain.model.Quotes
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface QuotesApi {
    @GET("forex/candle")
    fun getForexData(
        @Query("symbol") symbol: String,
        @Query("period") period: String,
        @Query("access_key") accessKey: String
    ): Call<Quotes>
}