package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen02ViewModel

@Composable
fun Screen02(vm : Screen02ViewModel){
    LaunchedEffect(Unit) {
        vm.initialize()
    }
    val charactersFound = vm.charactersFoundInDatabase.collectAsState()
    val characters = vm.characters.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(

            text = "Your Created Characters",
            fontSize = 25.sp
        )
    }

    if(charactersFound.value == true){
        LazyColumn(
            modifier = Modifier.height(710.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(characters.value){ character ->

            }
        }
    } else {
        Column(
            modifier = Modifier.height(710.dp)
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ,

            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No characters have been created yet.",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
            Spacer(Modifier.padding(5.dp))
            Text(
                text = "Go to \"Create\" screen to make characters.",
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                contentDescription = "Icon of arrow"
            )
        }
    }

}

@Composable
fun CreatedCharacterItem(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    ) {

    }
}

