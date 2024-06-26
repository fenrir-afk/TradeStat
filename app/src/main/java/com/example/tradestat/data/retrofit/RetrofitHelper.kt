package com.example.tradestat.data.retrofit

import android.util.Xml
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

object RetrofitHelper {

    private const val BASE_URL = "https://fcsapi.com/api-v3/"
    private const val MOEX_BASE_URL = "https://iss.moex.com/iss/"
    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}