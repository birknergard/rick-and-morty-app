package com.example.exam.dataClasses

import android.icu.util.Calendar
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.example.exam.data.Repository
import com.google.gson.annotations.SerializedName

@Entity
data class CreatedCharacter(
   @PrimaryKey
   val id : Int = createId(),

   @SerializedName("name")
   var name: String? = null,

   @SerializedName("gender")
   var gender: String? = null,

   @SerializedName("origin")
   var origin: String? = null,

   @SerializedName("species")
   var species: String? = null,

   @SerializedName("description")
   var description: String? = null,

   @SerializedName("created")
   var created: String = logCurrentDateTime(),
) {
    companion object{
        private var counter = 0
        private fun createId() : Int{
            val id = counter
            counter++
            return id
        }
        private fun logCurrentDateTime() : String{
            return Calendar.getInstance().time.toString()
        }
    }
    suspend fun uploadToDB(){
        Repository.insertCharacterIntoDB(this)
    }
}

