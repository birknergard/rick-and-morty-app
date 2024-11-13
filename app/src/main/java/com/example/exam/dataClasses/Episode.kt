package com.example.exam.dataClasses

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EpisodeData(
    val air_date: String,

    @SerializedName("characters")
    private val _appearingCharacterUrls: List<String>,

    val created: String,
    val episode: String,
    val id: Int,
    val name: String,
    val url: String
) {
    fun getAppearingCharacters() : List<String>{
        return _appearingCharacterUrls
    }
}

data class Episode(
    val data : EpisodeData,
    val appearingCharacters: MutableStateFlow<List<SimplifiedCharacter>> = MutableStateFlow(emptyList()),
    val toggle : MutableStateFlow<Boolean> = MutableStateFlow(false)
) {

    fun isToggled() : Boolean{
        return toggle.asStateFlow().value
    }

    fun toggleCharacterList(){
        this.toggle.value = !this.toggle.value
    }

    suspend fun updateCharacters(){
        setCharacters(getCharactersFromAPI())
        Log.d("Episode", "Appearing characters: ${appearingCharacters.value}")
    }
    fun listIsEmpty() : Boolean{
        return appearingCharacters.value.isEmpty()
    }

    private fun getAppearingCharacterURLs() : List<String>{
        return data.getAppearingCharacters()
    }
    private fun setCharacters(characters : List<SimplifiedCharacter>){
        this.appearingCharacters.value = characters
    }

    private fun getAppearingCharacterIds() : List<Int>{
        val parsedListOfIds = mutableListOf<Int>()
        getAppearingCharacterURLs().forEach { character : String ->
            val urlLength = character.length
            Log.d("Episode", "Url length ($urlLength)")
            when(urlLength){
                // Since the URL of the characters have the same length, i use the length
                // as the condition, and based on the size i take more or less digits.
                43 -> parsedListOfIds.add(character.takeLast(1).toInt())
                44 -> parsedListOfIds.add(character.takeLast(2).toInt())
                45 -> parsedListOfIds.add(character.takeLast(3).toInt())
            }
        }
        Log.d("Episode", "List of ids: $parsedListOfIds")
        return parsedListOfIds
    }

    private suspend fun getCharactersFromAPI() : List<SimplifiedCharacter>{
        return Repository.loadSimplifiedCharactersFromApi(this.getAppearingCharacterIds())
    }


}
