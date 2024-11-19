package com.example.exam.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.backend.Repository
import com.example.exam.dataClasses.character.Character
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen01ViewModel : ViewModel(){
    val navUIState = listOf(true, false, false, false)
    val characterList = MutableStateFlow(mutableStateListOf<Character>())

    val page = MutableStateFlow(1)
    val maxPage = MutableStateFlow<Int>(0)
    var apiCallSuccessful = MutableStateFlow<Boolean?>(null)

    suspend fun initialize(){
        if(apiCallSuccessful.value == null){
            page.value = 1
            initialCharacterLoad()
        }
        delay(200)
    }

    fun initialCharacterLoad(){
        viewModelScope.launch {
            val apiCall = Repository.fetchCharacters(1)
            if(apiCall.isSuccessful && apiCall.output.isNotEmpty()){
                apiCallSuccessful.value = true
                maxPage.value = apiCall.pages
                characterList.value.addAll(apiCall.output)
                delay(2000)
            } else {
                apiCallSuccessful.value = false
                delay(2000)
            }
        }
    }

    fun reachedMaxPage() : Boolean{
        return page.value == maxPage.value
    }
    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            val apiCall = Repository.fetchCharacters(page)
            if(apiCall.isSuccessful && apiCall.output.isNotEmpty()){
                apiCallSuccessful.value = true
                characterList.value.addAll(apiCall.output)
                delay(2000)
            } else {
                apiCallSuccessful.value = false
                delay(2000)
            }
        }
    }


}