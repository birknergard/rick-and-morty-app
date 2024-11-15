package com.example.exam.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
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
    val characterList = MutableStateFlow(mutableStateListOf<Character>())
    val page = MutableStateFlow(1)

    var apiCallSuccessful = MutableStateFlow<Boolean?>(null)

    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            val apiCall = Repository.loadCharactersFromApi(page)
            if(apiCall.first.isNotEmpty() && apiCall.second){
                apiCallSuccessful.value = true
                characterList.value.addAll(apiCall.first)
                delay(2000)
            } else {
                apiCallSuccessful.value = false
                delay(2000)
            }
        }
    }


}