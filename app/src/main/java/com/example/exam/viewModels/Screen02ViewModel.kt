package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.exam.data.Repository
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.SimplifiedCharacter
import kotlinx.coroutines.flow.MutableStateFlow

class Screen02ViewModel : ViewModel() {
    val characters = MutableStateFlow<List<CreatedCharacter>>(emptyList())

    suspend fun initialize(){
        characters.value = Repository.getCharactersFromDB()
        Log.d("Repository", "Number of characters in database: ${characters.value.size}")
    }

    fun hasCreatedCharacters() : Boolean{
        return characters.value.isNotEmpty()
    }


    val navUIState = listOf(false, true, false, false)
}