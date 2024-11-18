package com.example.exam.dataClasses.character

import android.icu.util.Calendar
import androidx.room.Entity
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

    fun getDate() : String{
        val splitDate = this.created.split(" ")
        return "${splitDate[2]}. ${splitDate[1]}. ${splitDate[splitDate.size - 1]}"
    }

    fun getGenderCapitalized() : String{
        val capitalized = this.gender!!
            .take(1)
            .uppercase() + this.gender!!
            .takeLast(this.gender!!.length - 1)
        return capitalized
    }


    suspend fun upload(){
        Repository.saveCharacter(this)
    }
}

