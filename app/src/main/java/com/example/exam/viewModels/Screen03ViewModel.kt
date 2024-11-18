package com.example.exam.viewModels

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
    // Character properties as stateflows.
    val name = MutableStateFlow("")
    private val gender = MutableStateFlow("")
    val origin = MutableStateFlow("")
    val species = MutableStateFlow("")
    val description = MutableStateFlow("")

    val allFieldsAreFilled = MutableStateFlow(true)

    fun verifyFields(){
        allFieldsAreFilled.value = checkIfAllFieldsAreFilled()
    }

    private fun checkIfAllFieldsAreFilled() : Boolean{
        val fields = mapOf<String, String>(
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

    val genderOptions = listOf("Male", "Female", "Genderless", "Unknown")

    // Wrote setters to prevent loading of state variables in UI code. May be redundant or unnecessary.
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
    private fun resetSelection(){
        for (i in 0 .. 3){
            _genderSelectionToggle.value[i] = false
        }
    }

    fun getSelectionToggleList() : SnapshotStateList<Boolean>{
        return _genderSelectionToggle.asStateFlow().value
    }

    fun getFilteredLocationList() : List<Location>{
        return getLocationList().filter { location ->
            location.name!!.lowercase().startsWith(
                origin.value.lowercase()
            )
        }
    }

    fun uploadCharacterToDB(){
        viewModelScope.launch {
            if(allFieldsAreFilled.value){
                val character = createCharacter()
                character.uploadToDB()
                clearAllFields()
            }
        }
    }

    fun clearAllFields(){
        setName("")
        setGender("")
        resetSelection()
        setOrigin("")
        setSpecies("")
        setDesc("")
    }

    private fun createCharacter() : CreatedCharacter{
       return CreatedCharacter(
           name = name.value,
           gender = gender.value,
           origin = origin.value,
           species = species.value,
           description = description.value,
       )
    }

    fun initializeLocationDatabase(){
        viewModelScope.launch{
            Repository.initializeLocationDatabase()
        }
    }

    private var _locationList = mutableStateOf<List<Location>>(emptyList())

    fun loadLocations(){
        viewModelScope.launch {
            _locationList.value = Repository.loadLocations()
        }
    }

    private fun getLocationList(): List<Location>{
        return _locationList.value
    }

    val navUIState = listOf(false, false, true, false)
}