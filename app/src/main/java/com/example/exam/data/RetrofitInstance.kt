package com.example.exam.data

import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.Info
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance{
    private val _url = "https://rickandmortyapi.com/"
    private val _httpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val _retrofit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl(_url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _rickAndMortyApiService = _retrofit.create(RickAndMortyApiService::class.java)

    suspend fun getAllCharactersFromApi(page : Int) : ApiResponse {
        val response = _rickAndMortyApiService.getAllCharacters(page)

        return if(response.isSuccessful){
            response.body() ?: ApiResponse(
                Info(0, 0, "none", "none"),
                listOf(Character())
            )

        } else {
            ApiResponse(
                Info(0, 0, "none", "none"),
                listOf(Character())
            )
        }
    }
}