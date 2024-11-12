package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

@Entity
data class EpisodeData(

    @PrimaryKey
    val id: Int,

    @SerializedName("air_date")
    val aired: String? = null,

    @SerializedName("characters")
    private val _appearingCharacterUrls: List<String>? = null,

    @Ignore
    val created: String,

    @SerializedName("episode")
    val episode: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @Ignore
    val url: String
) {
    fun getAppearingCharacters() : List<String>{
        return _appearingCharacterUrls!!
    }
}

data class Episode(
    val data : EpisodeData,
    var appearingCharacters: List<SimplifiedCharacter> = emptyList()
) {

    fun setCharacters(characters : List<SimplifiedCharacter>){
        this.appearingCharacters = characters
    }

    suspend fun updateCharacters(){
        setCharacters(getCharactersFromAPI())
    }
    private fun getAppearingCharacterURLs() : List<String>{
        return data.getAppearingCharacters()
    }

    fun getAppearingCharacterIds() : List<Int>{
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
