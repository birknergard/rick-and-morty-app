package com.example.exam.dataClasses

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

// Generic response class for parsing HTTP response
data class ApiResponse(
    @SerializedName("info") val info : Info,
    @SerializedName("result") val result : List<Character>
)

// Data class which adaps to which type of result we get.
sealed class ApiData  {
    data class CharacterList(val value : List<Character>) : ApiData()
    data class EpisodeList(val value : List<Episode>) : ApiData()
    data class LocationList(val value : List<Location>) : ApiData()
}
