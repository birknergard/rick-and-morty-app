package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.exam.dataClasses.Character
import com.example.exam.viewModels.Screen01ViewModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(viewModel: Screen01ViewModel){
    val vm = viewModel
    LazyColumn {
        items(vm.getCharList(2)){ character: Character ->
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
       Text(text = item.name)
   }
}