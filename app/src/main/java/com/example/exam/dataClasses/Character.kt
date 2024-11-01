package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

data class Character(
    val created: String? = null,

    val episode: List<String>? = null,

    val gender: String? = null,

    val id: Int? = null,

    val image: String? = null,

    val location: Location? = Location(),

    val name: String? = null,

    val origin: Location? = Location(),

    val species: String? = null,

    val status: String? = null,

    val type: String? = null,
    val url: String? = null
)
