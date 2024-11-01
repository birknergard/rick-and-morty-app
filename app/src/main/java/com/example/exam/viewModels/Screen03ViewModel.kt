package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen03ViewModel : ViewModel() {
    val createdCharacter = MutableStateFlow<Character?>(null)
    var uniqueId = MutableStateFlow(0)

    fun createUniqueID(){
        viewModelScope.launch {
            uniqueId.value = Repository.getUniqueID()
        }
        Log.d("ViewModel 3", "Unique ID created.")
    }

    fun uploadCharacterToDB(){
        viewModelScope.launch {
            createdCharacter.value!!.uploadToDB()
        }
    }

    fun setCharacterAttributes(){

    }

}