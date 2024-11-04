package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.exam.viewModels.Screen03ViewModel

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen03(viewModel: Screen03ViewModel){

    val createdCharacter = viewModel.createdCharacter.collectAsState()

    Column (modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 30.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = "Create your own character", fontSize = 25.sp)
        TextField(
            value = createdCharacter.value.name!!,
            onValueChange = { createdCharacter.value.name = it
            },
            label = { Text("Enter a name") }
        )

        GenderSelectionGrid(viewModel.genderOptions)

    }
}

@Composable
fun GenderSelectionGrid(options: List<String>){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 120.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 40.dp)
    ) {
        items(options){ option ->
            SelectButton(option)
        }

    }
}

@Composable
fun SelectButton(text : String){
    Surface(onClick = {

    }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.Black)
                .padding(vertical = 1.dp, horizontal = 1.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp)
            ,
            contentAlignment = Alignment.Center,

        ){
            Text(
                text = text,
            )
        }
    }
}

