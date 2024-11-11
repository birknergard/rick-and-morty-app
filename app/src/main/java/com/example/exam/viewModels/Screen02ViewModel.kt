package com.example.exam.viewModels

import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.exam.data.Repository
import com.example.exam.dataClasses.CreatedCharacter
import kotlinx.coroutines.flow.MutableStateFlow

class Screen02ViewModel {
    val characters = MutableStateFlow<List<CreatedCharacter>>(emptyList())
    val charactersFoundInDatabase = MutableStateFlow(false)

    suspend fun initialize(){
        characters.value = Repository.getCharactersFromDB()
        Log.d("Repository", "Character list from database: ${characters.value}")
        charactersFoundInDatabase.value = hasCreatedCharacters()
        Log.d("Screen02", "Characters found in database variable = ${charactersFoundInDatabase.value}")
    }

    private fun hasCreatedCharacters() : Boolean{
        return characters.value.isNotEmpty()
    }

    val navUIState = listOf(false, true, false, false)
}