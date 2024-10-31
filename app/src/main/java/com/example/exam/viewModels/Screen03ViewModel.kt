package com.example.exam.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen03ViewModel : ViewModel() {
    val createdCharacter = MutableStateFlow<Character?>(null)

    fun getUniqueID() : Int{
        viewModelScope.launch {
            val id = Repository.getUniqueID()
        }
        return id
    }

    fun uploadCharacterToDB(){
        viewModelScope.launch {
            createdCharacter.value!!.uploadToDB()
        }
    }

    fun setCharacterAttributes(){

    }

}