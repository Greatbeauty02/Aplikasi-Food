package com.uasppb.appfood054.Data.Connection

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

    val apiService: com.uasppb.appfood054.Data.Connection.ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(com.uasppb.appfood054.Data.Connection.RetrofitClient.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.uasppb.appfood054.Data.Connection.ApiService::class.java)
    }
}