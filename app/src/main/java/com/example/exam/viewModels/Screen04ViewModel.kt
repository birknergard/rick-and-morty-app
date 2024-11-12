package com.example.exam.viewModels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Episode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class Screen04ViewModel : ViewModel() {

    val episodes = MutableStateFlow<List<Episode>>(emptyList())
    val toggles = MutableStateFlow<MutableList<Boolean>>(mutableListOf())

    fun initialize(){
        if(episodeListIsEmpty()){
            getEpisodesFromApi(1)
        }
    }

    fun getEpisodesFromApi(page : Int){
        viewModelScope.launch {
            val response = Repository.fetchEpisodesFromAPI(page).second
            episodes.value = response
            Log.d("API", "Api call callback: ${response}")
            Log.d("Screen04VM", "Read-only list: ${episodes.value}")
            delay(2000)

        }
    }
    private fun episodeListIsEmpty() : Boolean{
        return episodes.value.isEmpty()
    }

    fun getCharactersByEpisode(episode: Episode){
        viewModelScope.launch {
            episode.updateCharacters()
        }
    }


    val navUIState = listOf(false, false, false, true)
}