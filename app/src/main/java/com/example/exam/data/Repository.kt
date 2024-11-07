package com.example.exam.data

import android.content.Context
import android.util.Log
import androidx.collection.objectIntMap
import androidx.room.Room
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location
import kotlinx.coroutines.delay

object Repository {
    // Database
    private lateinit var _appDatabase: AppDatabase
    private val _retrofit = RetrofitInstance()

    fun initDB(context : Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "rick-and-morty-database"
        ).build()
    }

    suspend fun insertCharacterIntoDB(character: CreatedCharacter){
        Log.e("DATABASE", "Inserting $character into database...")
        _appDatabase.rickAndMortyDao().insertCreatedCharacter(character)
    }

    suspend fun insertCharactersIntoDB(characters : List<CreatedCharacter>){
        Log.e("DATABASE", "Inserting multiple characters into database...")
        _appDatabase.rickAndMortyDao().insertCreatedCharacters(characters)
    }

    suspend fun getCharactersFromDB() : List<CreatedCharacter>{
        return _appDatabase.rickAndMortyDao().getCreatedCharacters()
    }

    suspend fun getCharacterByID(id : Int) : CreatedCharacter? {
        return _appDatabase.rickAndMortyDao().getCreatedCharacterById(id)
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
    suspend fun loadCharactersFromApi(page : Int) : Pair<List<Character>, Boolean>{
        val response = _retrofit.getAllCharactersFromApi(page)
        return response
    }

    suspend fun getLocationsFromAPI() : List<Location>{
        // Creates an empty list to hold new data.
        val parsedList = mutableListOf(Location())

        // loads the first response to check for page count
        val initResponse = _retrofit.getLocationsWithPagesFromApi(1)

        // Checks number of pages
        val pageCount = initResponse.second

        // makes one api per page to retrieve every location.
        for(i in 2 .. pageCount){
            val response = _retrofit.getLocationsFromApi(i)
            if(response.first){
                response.second.forEach{ location ->
                    parsedList.add(Location(
                        name = location.name,
                        url = location.url
                    ))
                }
                Log.i("LocationAPI","Parsed locations for page#${i} from the API.")
            }
        }

        Log.i("LocationAPI","Parsing complete.")
        return parsedList
    }

    suspend fun insertLocationsIntoDB(locationsFromAPI : List<Location>) : Boolean{
        val locationsInAPI = locationsFromAPI.size
        val locationsInDB : Int = _appDatabase.rickAndMortyDao().getLocationCount()

        if(locationsInAPI != locationsInDB) {
            if(locationsInDB > 0) {
                _appDatabase.rickAndMortyDao().wipeTable()
            }
            _appDatabase.rickAndMortyDao().insertLocationList(locationsFromAPI)
            return true

        } else {
            Log.i("DATABASE","Database already loaded.")
            return false
        }
    }
    suspend fun initializeLocationDB(){
        if(_appDatabase.rickAndMortyDao().getLocationCount() == 0){
            Log.d("DATABASE", "Database is empty, loading API.")
            val locationsFromAPI = getLocationsFromAPI()
            if (locationsFromAPI.isNotEmpty()){
                Log.d("API", "GET request returned results, insert into database.")
                _appDatabase.rickAndMortyDao().insertLocationList(locationsFromAPI)
            }
            delay(2000)
        }
    }

}