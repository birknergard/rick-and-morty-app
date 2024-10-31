package com.example.exam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.exam.dataClasses.Character

object Repository {
    // Database
    private lateinit var _appDatabase: AppDatabase
    fun initDB(context : Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "rick-and-morty-database"
        ).build()
    }

    suspend fun insertCharacterIntoDB(character: Character){
        Log.e("DATABASE", "Inserting $character into database...")
        _appDatabase.rickAndMortyDao().insertCharacter(character)
    }
    suspend fun insertCharactersIntoDB(characters : List<Character>){
        Log.e("DATABASE", "Inserting multiple characters into database...")
        _appDatabase.rickAndMortyDao().insertCharacters(characters)
    }

    suspend fun getCharactersFromDB() : List<Character>{
        return _appDatabase.rickAndMortyDao().getCharacters()
    }

    suspend fun getCharacterByID(id : Int) : Character? {
        return _appDatabase.rickAndMortyDao().getCharacterById(id)
    }
    suspend fun getUniqueID() : Int {
        val listOfIntegers = _appDatabase.rickAndMortyDao().getAllIds()

        // checks all ids in list for the highest one. If there is no ids (database is empty)
        // it returns 1, elsewhise it returns the highest number incremented by one.
        if (listOfIntegers == null){
            return 1
        } else {
            return listOfIntegers.max() + 1
        }
    }

    // API
    suspend fun loadCharactersFromApi(page : Int) : List<Character>{
        val response = RetrofitInstance().getAllCharactersFromApi(page)
        return response.result
    }

}