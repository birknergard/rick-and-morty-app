package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

@Entity
data class Character(
    @SerializedName("created")
    val created: String? = null,

    val episode: List<String>? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @PrimaryKey
    val id: Int? = null,

    val image: String? = null,
    val location: Location? = Location(),

    // This
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("origin")
    val origin: Location? = Location(),

    @SerializedName("species")
    val species: String? = null,

    val status: String? = null,

    @SerializedName("type")
    val type: String? = null,
    val url: String? = null
) {

    suspend fun uploadToDB(){
        Repository.insertCharacterIntoDB(this)
    }
}