package com.example.exam

import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Info
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RickAndMortyApi {
    private val _URL = "https.//rickandmortyapi.com/api"
    private val _httpClient = OkHttpClient
        .Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build()

    private val _retrofit = Retrofit.Builder()
        .client(_httpClient)
        .baseUrl(_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val _rickAndMortyApiService = _retrofit.create(RickAndMortyApiService::class.java)

    suspend fun getAllCharactersFromApi() : ApiResponse<ApiData.CharacterList> {
        val response = _rickAndMortyApiService.getAllCharacters()

        return if(response.isSuccessful){
            response.body() ?: ApiResponse(
                Info(0, 0, "none", "none"),
                ApiData.CharacterList(emptyList())
            )
        } else {
            ApiResponse(
                Info(0, 0, "none", "none"),
                ApiData.CharacterList(emptyList())
            )
        }
    }

    suspend fun getAllLocationsFromApi() : ApiResponse<ApiData.LocationList>{
        val response = _rickAndMortyApiService.getAllLocations()

        return if(response.isSuccessful){
            response.body() ?: ApiResponse(
                Info(0, 0, "none", "none"),
                ApiData.LocationList(emptyList())
            )
        } else {
            response.body() ?: ApiResponse(
                Info(0, 0, null, null),
                ApiData.LocationList(emptyList())
            )
        }
    }

    suspend fun getAllEpisodesFromApi() : ApiResponse<ApiData.EpisodeList>{
        val response = _rickAndMortyApiService.getAllEpisodes()

        return if(response.isSuccessful){
            response.body() ?: ApiResponse(
                Info(0, 0, null, null),
                ApiData.EpisodeList(emptyList())
            )
        } else {
            response.body() ?: ApiResponse(
                Info(0, 0, null, null),
                ApiData.EpisodeList(emptyList())
            )
        }
    }
}