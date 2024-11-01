package com.example.exam.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location

@Dao
interface RickAndMortyDao {
    @Query("SELECT * FROM CreatedCharacter")
    suspend fun getCharacters() : List<CreatedCharacter>

    @Query("SELECT id FROM CreatedCharacter")
    suspend fun getAllIds() : List<Int>?

    @Query("SELECT * FROM CreatedCharacter WHERE :characterId = id")
    suspend fun getCharacterById(characterId : Int) : CreatedCharacter?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character : CreatedCharacter)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CreatedCharacter>)

    @Query("SELECT * FROM Location")
    suspend fun getLocationsFromDB() : List<Location>

    @Query("SELECT * FROM Location WHERE :locationId = id")
    suspend fun getLocationByID(locationId : Int) : Location

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocationList(list : List<Location>)
}

@Database(entities = [CreatedCharacter::class, Location::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun rickAndMortyDao() : RickAndMortyDao
}