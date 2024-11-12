package com.example.exam.data

import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.EpisodeData
import com.example.exam.dataClasses.LocationFull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("api/character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<ApiResponse<List<Character>>>

    @GET("api/character/{ids}")
    suspend fun getMultipleCharacters(@Path("ids") listOfIds : List<Int>) : Response<List<Character>>

    @GET("api/location/")
    suspend fun getAllLocations(@Query("page") page : Int) : Response<ApiResponse<List<LocationFull>>>

    @GET("api/episode/")
    suspend fun getEpisodes(@Query("Page") page : Int) : Response<ApiResponse<List<EpisodeData>>>
}
