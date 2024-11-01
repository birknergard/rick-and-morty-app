package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Location(
    @PrimaryKey
    val id : Int? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("url")
    val url : String? = null
)

