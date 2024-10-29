package com.example.exam

import android.util.Log
import com.example.exam.dataClasses.ApiData

object Repository {
    val apiService = RickAndMortyApi()
    var characters = ApiData.CharacterList(emptyList())

    suspend fun loadCharactersFromApi(page : Int){
        val response = apiService.getAllCharactersFromApi(page)

        Log.e("GET /character", response.info.toString())
        characters = response.result
    }

}