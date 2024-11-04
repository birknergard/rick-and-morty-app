package com.example.exam.data

import com.example.exam.dataClasses.ApiResponse
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.LocationFull
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("/api/character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<ApiResponse<List<Character>>>

    @GET("/api/location/")
    suspend fun getAllLocations(@Query("page") page : Int) : Response<ApiResponse<List<LocationFull>>>
}
