package com.example.exam

import android.util.Log
import com.example.exam.dataClasses.ApiData

object Repository {
    val apiService = RickAndMortyApi()
    lateinit var characters : ApiData.CharacterList
    lateinit var locations : ApiData.LocationList
    lateinit var episodes : ApiData.EpisodeList

    suspend fun loadCharactersFromApi(){
        val response = apiService.getAllCharactersFromApi()
        Log.e("GET /character", response.info.toString())
        characters = response.result
    }

    suspend fun loadLocationsFromApi(){
        val response = apiService.getAllLocationsFromApi()
        Log.e("GET /location", response.info.toString())
        locations = response.result
    }

    suspend fun loadEpisodesFromApi(){
        val response = apiService.getAllEpisodesFromApi()
        Log.e("GET /episodes", response.info.toString())
        episodes = response.result
    }
}