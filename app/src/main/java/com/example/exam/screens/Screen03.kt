package com.example.exam.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.exam.viewModels.Screen03ViewModel

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen03(){
      val vm = Screen03ViewModel()
      Column (modifier = Modifier
          .fillMaxWidth(),
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally
      ){
            Text(text = "Create your own character", fontSize = 25.sp)
      }
}

