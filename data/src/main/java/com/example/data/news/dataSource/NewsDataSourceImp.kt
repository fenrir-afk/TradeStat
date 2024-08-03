package com.example.data.news.dataSource

import android.util.Log
import com.example.data.BuildConfig
import com.example.data.news.api.QuotesApi
import com.example.domain.model.Quotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NewsDataSourceImp @Inject constructor(private val api:QuotesApi):NewsDataSource {
    override fun getForexData(quotePair: String, time: String): Flow<Quotes> = flow {
        val response = api.getForexData(quotePair, time, BuildConfig.API_IPO_KEY).execute()
        if (response.isSuccessful) {
            if ( response.body() == null){
                response.body()!!.response //trigger exception if it is null (we catch it in retry block)
                Log.d("Retrofit", "Response body is null")
            }else{
                emit(response.body()!!)
            }
        } else {
            Log.d("Retrofit", "Response is not successful")
        }
    }
}