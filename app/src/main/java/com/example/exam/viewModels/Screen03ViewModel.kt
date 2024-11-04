package com.example.exam.viewModels

import android.icu.util.Calendar
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen03ViewModel : ViewModel() {
    val createdCharacter = MutableStateFlow<CreatedCharacter?>(null)
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

    fun setCharacterAttributes(
        gender : String,
        origin : Location,
        name : String,
        species : String,
        type : String,
        locationId : Int
    ){
       createUniqueID()
       createdCharacter.value = CreatedCharacter(
           id = uniqueId.value,
           created = "${Calendar.getInstance().time}",
           name = name,
           gender = gender,
           originId = locationId,
           species = species,
           type = type,
       )

    }

}