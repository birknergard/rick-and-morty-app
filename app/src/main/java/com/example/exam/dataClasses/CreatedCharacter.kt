package com.example.exam.dataClasses

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

@Entity(foreignKeys = [ForeignKey(
    entity = Location::class,
    parentColumns = ["id"],
    childColumns = ["originId"],
    onDelete = ForeignKey.CASCADE
)])
data class CreatedCharacter(
   @PrimaryKey
   val id : Int? = null,

   @SerializedName("name")
   val name: String? = null,

   @SerializedName("gender")
   val gender: String? = null,

   @SerializedName("originId")
   val originId: Int? = null,

   @SerializedName("species")
   val species: String? = null,

   @SerializedName("type")
   val type: String? = null,

    @SerializedName("created")
    val created: String? = null,
) {
    suspend fun uploadToDB(){
        Repository.insertCharacterIntoDB(this)
    }
}

