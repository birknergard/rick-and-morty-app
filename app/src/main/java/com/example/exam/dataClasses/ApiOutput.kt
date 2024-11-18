package com.example.exam.dataClasses


// This is just a Pair<Boolean, List<T>> for outputting Api information but for more readably code.
data class ApiOutput<T>(
    val isSuccessful : Boolean = false,
    val output : List<T>  = emptyList(),
    val pages : Int = 0
)


