package com.example.exam

import com.example.exam.dataClasses.Character

object Repository {

    suspend fun loadCharactersFromApi(page : Int) : List<Character>{
        val response = RetrofitInstance().getAllCharactersFromApi(page)
        return response.result
    }

}