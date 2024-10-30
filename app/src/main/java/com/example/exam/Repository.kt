package com.example.exam

import android.util.Log
import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.Character

object Repository {

    suspend fun loadCharactersFromApi(page : Int) : List<Character>{
        val response = RetrofitInstance().getAllCharactersFromApi(page)
        //response.result.forEach { char -> println(char.name) }
        return response.result
    }

}