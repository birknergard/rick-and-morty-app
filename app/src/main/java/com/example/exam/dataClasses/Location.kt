package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Location(
    @PrimaryKey
    val id : Int = getNextId(),
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("url")
    val url : String? = null
) {
    companion object{
        private var currentId = 0
        fun getNextId() : Int{
            currentId++
            return currentId
        }
    }
}

