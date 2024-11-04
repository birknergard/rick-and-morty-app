package com.example.exam.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

        TODO(
           "Input for origin, type, species and gender respectively."
        )
    }
}

@Composable
fun GenderSelection(){
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp)
    ) {

    }
}

