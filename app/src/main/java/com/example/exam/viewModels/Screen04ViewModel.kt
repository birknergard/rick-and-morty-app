package com.example.exam.viewModels

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.exam.backend.Repository
import com.example.exam.dataClasses.episode.Episode
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Screen04ViewModel : ViewModel() {

    private val episodes = MutableStateFlow<SnapshotStateList<Episode>>(mutableStateListOf())

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
        if(selectedSeason.value < 5){
            selectedSeason.value++
            filteredList.value = filterEpisodes(selectedSeason.value)
        }
    }
    fun selectPreviousSeason(){
        if(selectedSeason.value > 1){
            selectedSeason.value--
            filteredList.value = filterEpisodes(selectedSeason.value)
        }
    }

    private fun filterEpisodes(season : Int) : List<Episode>{
        return episodes.value.filter { episode: Episode ->
            episode.data.getSeasonAndEpisode().first == season
        }
    }

    private suspend fun getEpisodesFromApi(page : Int){
        //  Calls for all episodes on a given page and adds it to the mutableList "episodes"
            val response = Repository.fetchEpisodes(page).output
            episodes.value.addAll(response)
            Log.d("API", "Api call callback: $response")
            Log.d("Screen04VM", "Read-only list: ${episodes.value}")
            delay(200)

    }
    private suspend fun getAllEpisodesFromApi(){
        // This could be changed to a loop where the page count is retrieved first from api, then be ran
        // accordingly. But since it was only three pages i didn't bother.
        for (i in 1 .. 3){
            getEpisodesFromApi(i)
        }
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