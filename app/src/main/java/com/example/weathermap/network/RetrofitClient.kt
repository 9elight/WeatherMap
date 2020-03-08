package com.example.weathermap.network

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
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}