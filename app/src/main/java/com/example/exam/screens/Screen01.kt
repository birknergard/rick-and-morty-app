package com.example.exam.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(){
    Item()
}
@Composable
fun Item(){
   Row(
       modifier = Modifier
           .fillMaxWidth()
           .background(color = Color.White),
   ){

   }
}