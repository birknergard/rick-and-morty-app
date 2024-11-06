package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class Screen01ViewModel() : ViewModel(){
    val navUIState = listOf(true, false, false, false)
    val characterList = MutableStateFlow(listOf<Character>())
    val page = MutableStateFlow(1)

    var apiCallSuccessful = MutableStateFlow<Boolean?>(null)

    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            val apiCall = Repository.loadCharactersFromApi(page)
            apiCallSuccessful.value = apiCall.second
            characterList.value = apiCall.first

            delay(2000)
        }
    }
}