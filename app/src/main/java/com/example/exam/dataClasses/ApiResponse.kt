package com.example.exam.dataClasses

// Generic response class
data class ApiResponse(
    val info : Info,
    val result : ApiData
)

// Data class which adaps to which type of result we get.
sealed class ApiData  {
    data class Characters(val value : List<Character>) : ApiData()
    data class Episodes(val value : List<Episode>) : ApiData()
    data class Locations(val value : List<Location>) : ApiData()
}
