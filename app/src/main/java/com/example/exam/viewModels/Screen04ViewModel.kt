package com.example.exam.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.data.Repository
import com.example.exam.dataClasses.Episode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen04ViewModel : ViewModel() {

    val episodes = MutableStateFlow<SnapshotStateList<Episode>>(mutableStateListOf())

    val selectedSeason = MutableStateFlow(1)

    var filteredList = MutableStateFlow<List<Episode>>(emptyList())

    fun initialize(){
        viewModelScope.launch {
            if(episodeListIsEmpty()){
                getAllEpisodesFromApi()
                filteredList.value = filterEpisodes(1)
            }
        }
    }

    fun selectNextSeason(){
        if(selectedSeason.value != 5){
            selectedSeason.value++
            filteredList.value = filterEpisodes(selectedSeason.value)
            if(filteredList.value.size > 10){

            }
        }
    }
    fun selectPreviousSeason(){
        if(selectedSeason.value != 1){
            selectedSeason.value--
            filteredList.value = filterEpisodes(selectedSeason.value)
        }
    }

    fun filterEpisodes(season : Int) : List<Episode>{
        return episodes.value.filter { episode: Episode ->
            episode.data.getSeasonAndEpisode().first == season
        }
    }

    suspend fun getEpisodesFromApi(page : Int){
            val response = Repository.fetchEpisodesFromAPI(page).second
            episodes.value.addAll(response)
            Log.d("API", "Api call callback: ${response}")
            Log.d("Screen04VM", "Read-only list: ${episodes.value}")
            delay(200)

    }
    suspend fun getAllEpisodesFromApi(){
        getEpisodesFromApi(1)
        getEpisodesFromApi(2)
        getEpisodesFromApi(3)
    }

    private fun episodeListIsEmpty() : Boolean{
        return episodes.value.isEmpty()
    }

    fun toggleCharacterList(episode: Episode){
        viewModelScope.launch {
            if(episode.listIsEmpty()){
                episode.updateCharacters()
                Log.d("Episode", "Loading characterList ...")
                //delay(100)
            }
            episode.toggleCharacterList()
        }
    }



    val navUIState = listOf(false, false, false, true)
}