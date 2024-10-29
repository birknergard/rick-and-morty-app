package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.Repository
import com.example.exam.dataClasses.ApiData
import com.example.exam.dataClasses.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen01ViewModel : ViewModel(){

    private val _characterList = MutableStateFlow(Repository.characters.value)

    fun getCharList(page : Int) : List<Character>{
    //    Log.e("Character from API", _characterList.value.get(1).name)
        return _characterList.value
    }

    fun updateCharacterList(page : Int){
        viewModelScope.launch {
            Repository.loadCharactersFromApi(page)
        }
    }
}