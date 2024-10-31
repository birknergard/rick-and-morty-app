package com.example.exam.dataClasses

import com.google.gson.annotations.SerializedName

// Generic response class for parsing HTTP response
data class ApiResponse(
    @SerializedName("info") val info : Info,
    @SerializedName("results") val result : List<Character>
)
