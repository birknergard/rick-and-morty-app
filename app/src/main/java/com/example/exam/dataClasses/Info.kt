package com.example.exam.dataClasses

data class Info(
    val count : Int,
    val pages : Int,
    val next : String,
    val prev : String
){
    override fun toString() : String{
       return "count: ${this.count}, pages: ${this.pages}, next: ${this.next}, prev ${this.prev}"
    }
}