package com.example.exam.data

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.exam.dataClasses.Character

@Dao
interface RickAndMortyDao {
    @Query("SELECT * FROM Character")
    suspend fun getCharacters() : List<Character>

    @Query("SELECT id FROM Character")
    suspend fun getAllIds() : List<Int>?

    @Query("SELECT * FROM Character WHERE :characterId = id")
    suspend fun getCharacterById(characterId : Int) : Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character : Character)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)
}

@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){
    abstract fun rickAndMortyDao() : RickAndMortyDao
}