package com.example.exam.backend

import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.exam.dataClasses.ApiOutput
import com.example.exam.dataClasses.character.Character
import com.example.exam.dataClasses.character.CreatedCharacter
import com.example.exam.dataClasses.episode.Episode
import com.example.exam.dataClasses.episode.EpisodeData
import com.example.exam.dataClasses.location.Location
import com.example.exam.dataClasses.character.SimplifiedCharacter

object Repository {
    private lateinit var _appDatabase: AppDatabase
    private val _retrofit = RetrofitInstance()

//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
// DATABASE
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
    fun initializeDatabase(context : Context){
        _appDatabase = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "rick-and-morty-database"
        )
        //    .fallbackToDestructiveMigration() // For use on startup when table qualities change
            .build()
    }

    suspend fun saveCharacter(character: CreatedCharacter){
        Log.d("DATABASE", "Inserting $character into database...")
        _appDatabase.rickAndMortyDao().insertCreatedCharacter(character)
    }

    suspend fun loadCharacters() : List<CreatedCharacter>{
        return _appDatabase.rickAndMortyDao().getCreatedCharacters()
    }

    suspend fun deleteCharacters(){
        _appDatabase.rickAndMortyDao().deleteCreatedCharacters()
    }
    private suspend fun loadLocations(locationsFromAPI : List<Location>){
        _appDatabase.rickAndMortyDao().insertLocationList(locationsFromAPI)
    }

    suspend fun initializeLocationDatabase(){
        val numberOfLocationsFromDB : Int = _appDatabase.rickAndMortyDao().getLocationCount()

        Log.d("DATABASE", "There are ${_appDatabase.rickAndMortyDao().getDistinctLocations()} " +
                "distinct locations in the database, and ${_appDatabase.rickAndMortyDao().getLocationCount()} total.")

        if(numberOfLocationsFromDB == 0){
            Log.d("DATABASE", "Database is empty, loading API ...")
            val apiResponse = fetchAllLocations()


            if (apiResponse.isSuccessful){
                //Log.d("API", "GET request returned ${locationsList.size} results, inserting into database ...")
                loadLocations(apiResponse.output)
                //Log.d("DATABASE", "Inserted ${locationsList.size} rows into DB. There are now ${_appDatabase.rickAndMortyDao().getDistinctLocations()} distinct locations.")
            }

        } else if(numberOfLocationsFromDB > 0){
            Log.d("DATABASE", "Database already has entries, verifying ...")
            val numberOfLocationsFromAPI = _retrofit.CharacterAPI().fetch(1).output.size + 1


            if(numberOfLocationsFromDB != numberOfLocationsFromAPI){
                //Log.d("DATABASE", "Mismatch between database(${numberOfLocationsFromDB}) and API(${numberOfLocationsFromAPI}). Reloading local data ...")
                val apiResponse = fetchAllLocations()

                if(apiResponse.isSuccessful){
                    //Log.d("API", "GET request returned ${numberOfLocationsFromAPI} results, inserting into database ...")
                    _appDatabase.rickAndMortyDao().deleteAllLocations()
                    //Log.d("DATABASE", "Number of rows in database after wipe: ${_appDatabase.rickAndMortyDao().getLocationCount()}")
                    loadLocations(apiResponse.output)
                    //Log.d("DATABASE", "Number of rows in database after insert: ${_appDatabase.rickAndMortyDao().getLocationCount()}")
                }

            } else {
                Log.d("DATABASE","Database already loaded with correct number of entries.")
            }
        }
        // Debugging
        // _appDatabase.rickAndMortyDao().getLocationNames().forEach{locationName ->
        //     Log.d("DATABASE", "${locationName.toString()}")
    }

    suspend fun loadLocations() : List<Location>{
        return _appDatabase.rickAndMortyDao().getLocationsFromDB()
    }

//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
// API
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::://
    suspend fun fetchCharacters(page : Int) : ApiOutput<Character>{
        return _retrofit.characterAPI.fetch(page)
    }

    suspend fun fetchSimplifiedCharacters(listOfIds : List<Int>) : List<SimplifiedCharacter>{
        val characterList = mutableListOf<SimplifiedCharacter>()
        val response = _retrofit.characterAPI.fetch(listOfIds)

        if(response.isSuccessful){
            response.output.forEach { character ->
                characterList.add(character.getSimplifiedCharacter())
            }
        }
        return characterList
    }

    private suspend fun fetchAllLocations() : ApiOutput<Location>{
        // Creates an empty list to hold new data.
        val parsedList = mutableListOf<Location>()

        // loads the first response to check for page count
        val initResponse = _retrofit.locationAPI.fetch(1)

        if(initResponse.isSuccessful) {
            val pageCount = initResponse.pages
            Log.d("API", "Number of pages(${pageCount}).")
            initResponse.output.forEach { location ->
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
                val response = _retrofit.locationAPI.fetch(i)
                if (response.isSuccessful) {
                    response.output.forEach { location ->
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
                    return ApiOutput()
                }
            }
            Log.d("LocationAPI", "Parsing complete.")
            return ApiOutput()
        } else {
            Log.d("API", "Error in api fetch.")
            return ApiOutput()
        }
    }

    suspend fun fetchEpisodes(page : Int) : ApiOutput<Episode>{
        val response = _retrofit.episodeAPI.get(page)
        val parsedEpisodes = mutableListOf<Episode>()


        if(response.isSuccessful == true){
            Log.d("API", "Api call fetchEpisodesFromAPI() successful")
            response.output.forEach { data : EpisodeData ->
                parsedEpisodes.add(Episode(data))
            }
            return ApiOutput(
                isSuccessful = true,
                output = parsedEpisodes
            )
        } else {
            return ApiOutput()
        }
    }
}