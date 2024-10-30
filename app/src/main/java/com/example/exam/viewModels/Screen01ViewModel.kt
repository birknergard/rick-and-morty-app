package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.Repository
import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen01ViewModel(val repo: Repository) : ViewModel(){

    val characterList = MutableStateFlow(listOf(Character(), Character()))
    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            characterList.value = repo.loadCharactersFromApi(page)
        }
    }
}