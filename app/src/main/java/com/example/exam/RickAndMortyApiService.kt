package com.example.exam

import com.example.exam.dataClasses.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface RickAndMortyApiService {
    @GET("api/character/")
    suspend fun getAllCharacters(@Query("page") page: Int): Response<ApiResponse>
}