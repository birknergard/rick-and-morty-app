package com.example.exam.dataClasses

import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.flow.MutableStateFlow

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
    var appearingCharacters: MutableStateFlow<List<SimplifiedCharacter>> = MutableStateFlow(emptyList())
) {

    suspend fun updateCharacters(){
        setCharacters(getCharactersFromAPI())
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
            when(urlLength){
                46 -> parsedListOfIds.add(character.takeLast(1).toInt())
                47 -> parsedListOfIds.add(character.takeLast(2).toInt())
                48 -> parsedListOfIds.add(character.takeLast(3).toInt())
            }
        }
        return parsedListOfIds
    }

    private suspend fun getCharactersFromAPI() : List<SimplifiedCharacter>{
        return Repository.loadSimplifiedCharactersFromApi(getAppearingCharacterIds())
    }


}
