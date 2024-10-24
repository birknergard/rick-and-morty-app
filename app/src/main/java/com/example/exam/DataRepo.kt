package com.example.exam

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRepo {
    private val _url = "https.//rickandmortyapi.com/api"
    private val _httpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        .build()

    private val _Parcel = Retrofit.Builder()
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}