package com.example.exam

import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.ApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface RickAndMortyApiService {

    @GET("/character")
    suspend fun getAllCharacters() : Response<ApiResponse<ApiData.CharacterList>>

}