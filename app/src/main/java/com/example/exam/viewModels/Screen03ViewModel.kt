package com.example.exam.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.character.CreatedCharacter
import com.example.exam.dataClasses.location.Location
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Screen03ViewModel : ViewModel() {
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  Character field handling
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    val name = MutableStateFlow("")
    private val gender = MutableStateFlow("")
    val origin = MutableStateFlow("")
    val species = MutableStateFlow("")
    val description = MutableStateFlow("")

    val allFieldsAreFilled = MutableStateFlow(true)

    private fun verifyFields(){
        allFieldsAreFilled.value = checkIfAllFieldsAreFilled()
    }

    private fun checkIfAllFieldsAreFilled() : Boolean{
        val fields = mapOf(
            "name" to name.value,
            "gender" to gender.value,
            "origin" to origin.value,
            "species" to species.value,
            "desc" to description.value
        )
        fields.forEach { (fieldName, fieldValue) ->
            if(fieldValue.length <= 2){
                Log.d("Screen03", "Checked field returned false, -${fieldName} : $fieldValue")
                return false
            }
        }
        return true
    }

    fun clearAllFields(){
        setName("")
        setGender("")
        resetSelection()
        setOrigin("")
        setSpecies("")
        setDesc("")
    }

    fun setName(name : String){
        this.name.value = name
    }
    private fun setGender(gender : String){
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

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
// Gender select
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    val genderOptions = listOf("Male", "Female", "Genderless", "Unknown")

    private val _genderSelectionToggle = MutableStateFlow(mutableStateListOf(false, false, false, false))

    private fun resetSelection(){
        for (i in 0 .. 3){
            _genderSelectionToggle.value[i] = false
        }
    }

    fun getSelectionToggleList() : SnapshotStateList<Boolean>{
        return _genderSelectionToggle.asStateFlow().value
    }

    fun select(i : Int, gender : String){
        _genderSelectionToggle.value[i] = true
        _genderSelectionToggle.value.indices.forEach { switchIndex ->
            if(switchIndex != i){
                _genderSelectionToggle.value[switchIndex] = false
            }
        }

        setGender(gender.lowercase())
    }


//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  Database
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    fun upload(){
        viewModelScope.launch {
            verifyFields()
            if(allFieldsAreFilled.value){
                val character = createCharacter()
                character.upload()
                clearAllFields()
            }
        }
    }

    fun initializeLocationDatabase(){
        viewModelScope.launch{
            Repository.initializeLocationDatabase()
        }
    }


//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  Create new character
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private fun createCharacter() : CreatedCharacter {
       return CreatedCharacter(
           name = name.value,
           gender = gender.value,
           origin = origin.value,
           species = species.value,
           description = description.value,
       )
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  Origin selection
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private var _locationList = mutableStateOf<List<Location>>(emptyList())

    fun loadLocations(){
        viewModelScope.launch {
            _locationList.value = Repository.loadLocations()
        }
    }

    private fun getLocationList(): List<Location>{
        return _locationList.value
    }

    fun getFilteredLocationList() : List<Location>{
        return getLocationList().filter { location ->
            location.name!!.lowercase().startsWith(
                origin.value.lowercase()
            )
        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//  Navigation button states.
//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    val navUIState = listOf(false, false, true, false)
}