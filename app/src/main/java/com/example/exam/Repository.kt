package com.example.exam

import android.util.Log
import com.example.exam.dataClasses.ApiData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object Repository {
    val apiService = RickAndMortyApi()
    val characters = MutableStateFlow(ApiData.CharacterList(emptyList()))

    suspend fun loadCharactersFromApi(){
        val response = apiService.getAllCharactersFromApi()
        Log.e("GET /character", response.info.toString())
        characters.value = response.result
    }

}