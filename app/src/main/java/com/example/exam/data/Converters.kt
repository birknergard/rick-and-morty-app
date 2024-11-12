package com.example.exam.data

import androidx.room.TypeConverter

class Converters {
    fun fromStringList(stringList : List<String>?) : String{

        if(stringList.isNullOrEmpty()){
            return ""
        }

        val parsedListOfIds = mutableListOf<Int>()
        stringList.forEach { character : String ->
            val urlLength = character.length
            when(urlLength){
                46 -> parsedListOfIds.add(character.takeLast(1).toInt())
                47 -> parsedListOfIds.add(character.takeLast(2).toInt())
                48 -> parsedListOfIds.add(character.takeLast(3).toInt())
            }
        }
        return parsedListOfIds.joinToString(",")
    }

    fun toIntList(data : String) : List<Int>{
        val output : List<Int>
        val stringList = data.split(",")
        output = stringList.mapNotNull { it.toIntOrNull() }
        return output
    }
}