package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.exam.dataClasses.Character
import com.example.exam.viewModels.Screen01ViewModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(vm: Screen01ViewModel){
    // Setup
    vm.updateCharacterList(1)

    val characters = vm.characterList.collectAsState()

    // Start of UI
    LazyColumn (
        modifier = Modifier
            .fillMaxWidth()
    ){
        items(characters.value){ character ->
            Item(character)
        }
    }
}

@Composable
fun Item(item : Character){
   Row(
       modifier = Modifier
           .fillMaxWidth()
           .background(color = Color.White),
   ){
       item.name?.let {
           Text(
               text = it,
               fontSize = 25.sp,
               textAlign = TextAlign.Center
           )
       }
   }
}