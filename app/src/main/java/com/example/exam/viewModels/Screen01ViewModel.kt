package com.example.exam.viewModels

import androidx.lifecycle.ViewModel
import com.example.exam.Repository

class Screen01ViewModel : ViewModel(){

    val characterList = Repository.characters


}