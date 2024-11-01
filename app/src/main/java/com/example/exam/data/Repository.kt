package com.example.exam.data

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location

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

    suspend fun insertCharacterIntoDB(character: CreatedCharacter){
        Log.e("DATABASE", "Inserting $character into database...")
        _appDatabase.rickAndMortyDao().insertCharacter(character)
    }
    suspend fun insertCharactersIntoDB(characters : List<CreatedCharacter>){
        Log.e("DATABASE", "Inserting multiple characters into database...")
        _appDatabase.rickAndMortyDao().insertCharacters(characters)
    }

    suspend fun getCharactersFromDB() : List<CreatedCharacter>{
        return _appDatabase.rickAndMortyDao().getCharacters()
    }

    suspend fun getCharacterByID(id : Int) : CreatedCharacter? {
        return _appDatabase.rickAndMortyDao().getCharacterById(id)
    }
    suspend fun getUniqueID() : Int {
        val listOfIntegers = _appDatabase.rickAndMortyDao().getAllIds()

        // checks all ids in list for the highest one. If there is no ids (database is empty)
        // it returns 1, otherwise it returns the highest number incremented by one.
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

    suspend fun getSimpleLocationsFromApi() : List<Location>{
        val parsedList = mutableListOf(Location())
        for(i in 1 .. 7){
            val response = RetrofitInstance().getAllLocationsFromApi(i)
            for(k in 1 .. 20){
                parsedList.add(Location(
                    response.result.get(i).id,
                    response.result.get(i).name,
                    response.result.get(i).url
                ))
            }
        }
        return parsedList
    }

    suspend fun insertCondensedLocationsFromApiIntoDB() : Boolean{
        if(_appDatabase.rickAndMortyDao().getLocationsFromDB().isEmpty()){
            val locations = getSimpleLocationsFromApi()
            _appDatabase.rickAndMortyDao().insertLocationList(locations)
            return true
        } else {
            return false
        }
    }
}