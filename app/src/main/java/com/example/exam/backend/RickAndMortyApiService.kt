package com.example.exam.backend

import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.character.Character
import com.example.exam.dataClasses.episode.EpisodeData
import com.example.exam.dataClasses.location.LocationFull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("api/character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<ApiResponse<Character>>

    @GET("api/character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") listOfIds : List<Int>) : Response<List<Character>>

    @GET("api/location/")
    suspend fun getAllLocations(@Query("page") page : Int) : Response<ApiResponse<LocationFull>>

    @GET("api/episode/")
    suspend fun getEpisodes(@Query("page") page : Int) : Response<ApiResponse<EpisodeData>>
}
