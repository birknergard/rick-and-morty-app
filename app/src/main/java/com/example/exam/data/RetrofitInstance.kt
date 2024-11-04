package com.example.exam.data

import android.util.Log
import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.Info
import com.example.exam.dataClasses.LocationFull
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.UnknownHostException

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

    suspend fun getAllCharactersFromApi(page : Int) : ApiResponse<List<Character>> {
        try {
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

        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return ApiResponse(
                Info(0, 0, "none", "none"),
                listOf(Character())
            )
        }
    }
    suspend fun getAllLocationsFromApi(page : Int) : ApiResponse<List<LocationFull>>{
        try {
            val response = _rickAndMortyApiService.getAllLocations(page)

            return if(response.isSuccessful){
                response.body() ?: ApiResponse(
                    Info(0,0,"none","none"),
                    listOf(LocationFull())
                )
            } else {
                ApiResponse(
                    Info(0,0,"none","none"),
                    listOf(LocationFull())
                )
            }
        } catch (e : UnknownHostException){
            Log.e("API","Could not establish connection to API.")
            return ApiResponse(
                Info(0,0,"none","none"),
                listOf(LocationFull())
            )
        }
    }
}