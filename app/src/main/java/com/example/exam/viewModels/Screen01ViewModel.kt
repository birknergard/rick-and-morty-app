package com.example.exam.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen01ViewModel() : ViewModel(){
    val characterList = MutableStateFlow(listOf<Character>())
    val page = MutableStateFlow(1)
    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            characterList.value = Repository.loadCharactersFromApi(page)
        }
    }
}