package com.example.exam.viewModels

import android.icu.util.Calendar
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Screen03ViewModel : ViewModel() {
    val createdCharacter = MutableStateFlow(CreatedCharacter(
        name = "",
        id = null,
        gender = null,
        originId = null,
        species = null,
        description = "",
        created = null
    ))
    var uniqueId = MutableStateFlow(0)

    val genderOptions = listOf("Male", "Female", "Genderless", "Unknown")

    private val _genderSelectionToggle = MutableStateFlow(mutableStateListOf(false, false, false, false))
    fun getSelectionToggleList() : SnapshotStateList<Boolean>{
        return _genderSelectionToggle.asStateFlow().value
    }

    val speciesOptions = listOf("Human", "Alien")

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
        name : String,
        species : String,
        description : String,
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
           description = description,
       )

    }

    val navUIState = listOf(false, false, true, false)

    fun initializeLocationDatabase(){
        viewModelScope.launch{
            Repository.initializeLocationDB()
        }
    }

    private var _locationList = mutableStateOf<List<Location>>(emptyList())

    fun loadLocations(){
        viewModelScope.launch {
            _locationList.value = Repository.getLocations()
        }
    }
    fun getLocationList(): List<Location>{
        return _locationList.value
    }

}