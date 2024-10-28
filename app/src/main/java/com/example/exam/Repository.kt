package com.example.exam

import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Repository {

    private const val _URL = "https.//rickandmortyapi.com/api"
    private val _httpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
        .build()

    private val _retrofit = Retrofit.Builder()
        .baseUrl(_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _rickAndMortyApiService = _retrofit.create(RickAndMortyApiService::class.java)

    suspend fun getAllCharactersFromApi() : ApiResponse<ApiData.CharacterList>?{
        val response = _rickAndMortyApiService.getAllCharacters()

        if(response.isSuccessful){
            return response.body()
        } else {
            return null
        }
    }
}