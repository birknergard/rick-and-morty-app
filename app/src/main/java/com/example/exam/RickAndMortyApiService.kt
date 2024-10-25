package com.example.exam

import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.ApiResponse
import retrofit2.http.GET

interface RickAndMortyApiService {
    @GET("/character")
    fun getCharacters() : ApiResponse<ApiData.CharacterList>

    @GET("/episode")
    fun getEpisodes() : ApiResponse<ApiData.EpisodeList>

    @GET("/location")
    fun getLocations() : ApiResponse<ApiData.LocationList>
}