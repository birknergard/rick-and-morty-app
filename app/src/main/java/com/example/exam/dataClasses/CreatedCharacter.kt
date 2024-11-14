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
   @PrimaryKey(autoGenerate = true)
   val id : Int = 0,

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
   var created: String = createCurrentDate(),
) {
    companion object{
        private fun createCurrentDate() : String{
            val datetime = Calendar.getInstance().time.toString()
            return datetime//.removeRange(9,29)
        }
    }



    suspend fun uploadToDB(){
        Repository.insertCharacterIntoDB(this)
    }
}

