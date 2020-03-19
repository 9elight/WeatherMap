package com.example.weathermap.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    companion object{
        fun instateRetrofit(baseUrl:String): ApiService?{
            val okHttpClient : OkHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(40,TimeUnit.SECONDS)
                .readTimeout(40,TimeUnit.SECONDS)
                .writeTimeout(40,TimeUnit.SECONDS)
                .build()
            val retrofit = Retrofit.Builder()
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}