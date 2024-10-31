package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

@Entity
data class Character(
    @SerializedName("created")
    val created: String = "",

    val episode: List<String> = emptyList(),

    @SerializedName("gender")
    val gender: String = "",

    @PrimaryKey
    val id: Int = 0,

    val image: String = "",
    val location: Location = Location(),

    // This
    @SerializedName("name")
    val name: String = "",

    @SerializedName("origin")
    val origin: Origin = Origin(),

    @SerializedName("species")
    val species: String = "",

    val status: String = "",

    @SerializedName("type")
    val type: String = "",
    val url: String = ""
) {
    data class Location(
        val name: String = "",
        val url: String = ""
    )

    data class Origin(
        @SerializedName("location_name")
        val name: String = "",
        @SerializedName("location_url")
        val url: String = ""
    )

    suspend fun uploadToDB(){
        Repository.insertCharacterIntoDB(this)
    }
}