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
    var name = MutableStateFlow("")
    val gender = MutableStateFlow("")
    val origin = MutableStateFlow("")
    val species = MutableStateFlow("")
    val description = MutableStateFlow("")

    val allFieldsAreFilled = MutableStateFlow(false)

    fun checkIfAllFieldsAreFilled() : Boolean{
        val fields = listOf(name, gender, origin, species, description)
        fields.forEach { field ->
            if(field.value != "" || field.value.length >= 2){
                return false
            }
        }
        return true
    }



    val genderOptions = listOf("Male", "Female", "Genderless", "Unknown")

    fun setName(name : String){
        this.name.value = name
    }
    fun setGender(gender : String){
        this.gender.value = gender
    }
    fun setOrigin(location : String){
        this.origin.value = location
    }
    fun setSpecies(species : String){
        this.species.value = species
    }
    fun setDesc(description : String){
        this.description.value = description
    }


    private val _genderSelectionToggle = MutableStateFlow(mutableStateListOf(false, false, false, false))
    fun getSelectionToggleList() : SnapshotStateList<Boolean>{
        return _genderSelectionToggle.asStateFlow().value
    }

    val speciesOptions = listOf("Human", "Alien")

    fun uploadCharacterToDB(){
        viewModelScope.launch {
            if(allFieldsAreFilled.value){
                val character = createCharacter()
                character.uploadToDB()
            }
        }
    }

    fun createCharacter() : CreatedCharacter{
       return CreatedCharacter(
           name = name.value,
           gender = gender.value,
           origin = origin.value,
           species = species.value,
           description = description.value,
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