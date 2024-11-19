package com.example.exam.backend

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.exam.dataClasses.character.CreatedCharacter
import com.example.exam.dataClasses.location.Location

@Dao
interface RickAndMortyDao {
    // For created character database
    @Query("DELETE FROM CreatedCharacter")
    suspend fun deleteCreatedCharacters()

    @Query("SELECT * FROM CreatedCharacter")
    suspend fun getCreatedCharacters() : List<CreatedCharacter>

    @Query("SELECT * FROM CreatedCharacter WHERE :characterId = id")
    suspend fun getCreatedCharacterById(characterId : Int) : CreatedCharacter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreatedCharacter(character : CreatedCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCreatedCharacters(characters: List<CreatedCharacter>)

    // For location database
    @Query("SELECT * FROM Location ORDER BY name DESC")
    suspend fun getLocationsFromDB() : List<Location>

    @Query("SELECT * FROM Location WHERE :locationId = id")
    suspend fun getLocationByID(locationId : Int) : Location

    @Query("SELECT COUNT(id) FROM Location")
    suspend fun getLocationCount() : Int

    @Query("SELECT DISTINCT COUNT(name) FROM Location")
    suspend fun getDistinctLocations() : Int

    @Query("DELETE FROM Location")
    suspend fun deleteAllLocations()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationList(list : List<Location>)
}

@Database(entities = [CreatedCharacter::class, Location::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun rickAndMortyDao() : RickAndMortyDao
}