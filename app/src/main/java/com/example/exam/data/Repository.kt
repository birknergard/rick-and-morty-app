package com.example.exam.data

import android.content.Context
import androidx.room.Room
import com.example.exam.dataClasses.Character

object Repository {
    private lateinit var _appDatabase: AppDatabase
    fun initDB(context : Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "rick-and-morty-database"
        ).build()
    }

    suspend fun loadCharactersFromApi(page : Int) : List<Character>{
        val response = RetrofitInstance().getAllCharactersFromApi(page)
        return response.result
    }

}