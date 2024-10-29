package com.example.exam.dataClasses

// Generic response class for parsing HTTP response
data class ApiResponse<T : ApiData>(
    val info : Info,
    val result : T
){
    fun getCount() : Int{
        return info.count
    }
}

// Data class which adaps to which type of result we get.
sealed class ApiData  {
    data class CharacterList(val value : List<Character>) : ApiData()
    data class EpisodeList(val value : List<Episode>) : ApiData()
    data class LocationList(val value : List<Location>) : ApiData()
}
