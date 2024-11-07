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
    private val _retrofit = RetrofitInstance()

    fun initDB(context : Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "rick-and-morty-database"
        ).build()
    }

    suspend fun insertCharacterIntoDB(character: CreatedCharacter){
        Log.d("DATABASE", "Inserting $character into database...")
        _appDatabase.rickAndMortyDao().insertCreatedCharacter(character)
    }

    suspend fun insertCharactersIntoDB(characters : List<CreatedCharacter>){
        Log.d("DATABASE", "Inserting multiple characters into database...")
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

    private suspend fun getLocationsFromAPI() : Pair<Boolean, List<Location>>{
        // Creates an empty list to hold new data.
        val parsedList = mutableListOf<Location>()

        // loads the first response to check for page count
        val initResponse = _retrofit.getLocationsWithPagesFromApi(1)

        if(initResponse.first) {
            val pageCount = initResponse.second
            Log.d("API", "Number of pages(${pageCount}).")
            initResponse.third.forEach { location ->
                parsedList.add(
                    Location(
                        name = location.name,
                        url = location.url
                    )
                )
            }
            Log.d("LocationAPI", "Parsed locations for page#1 from the API.")
            // makes one api per page to retrieve every location.
            for (i in 2..pageCount) {
                val response = _retrofit.getLocationsFromApi(i)
                if (response.first) {
                    response.second.forEach { location ->
                        parsedList.add(
                            Location(
                                name = location.name,
                                url = location.url
                            )
                        )
                    }
                    Log.d("LocationAPI", "Parsed locations for page#${i} from the API.")
                } else {
                    Log.d("LocationAPI", "Error in fetch. Cant find data for page#${i}.")
                    return Pair(false, emptyList())
                }
            }
            Log.d("LocationAPI", "Parsing complete.")
            return Pair(true, parsedList)
        } else {
            Log.d("API", "Error in api fetch.")
            return Pair(false, emptyList())
        }
    }

    suspend fun insertLocationsIntoDB(locationsFromAPI : List<Location>){
        _appDatabase.rickAndMortyDao().insertLocationList(locationsFromAPI)
    }

    suspend fun initializeLocationDB(){
        val locationsFromAPI : List<Location>
        val numberOfLocationsFromDB : Int = _appDatabase.rickAndMortyDao().getLocationCount()

        Log.d("DATABASE", "There are ${_appDatabase.rickAndMortyDao().getDistinctLocations()} " +
                "distinct locations in the database, and ${_appDatabase.rickAndMortyDao().getLocationCount()} total.")

        if(numberOfLocationsFromDB == 0){
            Log.d("DATABASE", "Database is empty, loading API ...")
            val apiResponse = getLocationsFromAPI()

            val isSuccessful = apiResponse.first
            locationsFromAPI = apiResponse.second

            if (isSuccessful){
                Log.d("API", "GET request returned ${locationsFromAPI.size} results, inserting into database ...")
                _appDatabase.rickAndMortyDao().insertLocationList(locationsFromAPI)
                Log.d("DATABASE", "Inserted ${locationsFromAPI.size} rows into DB. There are now ${_appDatabase.rickAndMortyDao().getDistinctLocations()} " +
                        "distinct locations.")
            }

        } else if(numberOfLocationsFromDB > 0){
            val numberOfLocationsFromAPI = _retrofit.getLocationCountFromAPI()


            if(numberOfLocationsFromDB != numberOfLocationsFromAPI){
                Log.d("DATABASE", "Mismatch between database(${numberOfLocationsFromDB}) and API(${numberOfLocationsFromAPI}). Reloading local data ...")
                val apiResponse = getLocationsFromAPI()
                val isSuccessful = apiResponse.first
                val locationList = apiResponse.second

                if(isSuccessful){
                    Log.d("API", "GET request returned ${numberOfLocationsFromAPI} results, inserting into database ...")
                    _appDatabase.rickAndMortyDao().wipeTable()
                    Log.d("DATABASE", "Number of rows in database after wipe: ${_appDatabase.rickAndMortyDao().getLocationCount()}")
                    _appDatabase.rickAndMortyDao().insertLocationList(locationList)
                    Log.d("DATABASE", "Number of rows in database after insert: ${_appDatabase.rickAndMortyDao().getLocationCount()}")
                }

            } else {
                Log.d("DATABASE","Database already loaded with correct number of entries.")
            }
        }
        // Debugging
       // _appDatabase.rickAndMortyDao().getLocationNames().forEach{locationName ->
       //     Log.d("DATABASE", "${locationName.toString()}")
    }

}