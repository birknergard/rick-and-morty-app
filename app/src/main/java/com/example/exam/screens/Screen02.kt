package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen02ViewModel

val componentHeight = 700.dp

@Composable
fun Screen02(vm : Screen02ViewModel){
    LaunchedEffect(Unit) {
        vm.initialize()
    }
    val characters = vm.characters.collectAsState()

    Column(modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "Your Created Characters",
            fontSize = 25.sp
        )
        Spacer(Modifier
            .padding(top = 5.dp)
            .height(2.dp)
            .fillMaxWidth()
            .background(Color.Gray)
        )
    }

    if(vm.hasCreatedCharacters()){
        LazyColumn(
            modifier = Modifier.height(componentHeight)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(characters.value){ character ->
                Spacer(Modifier.height(15.dp))
                CreatedCharacterItem(character)
            }
        }
    } else {
        Column(
            modifier = Modifier.height(componentHeight)
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
        }
    }

}

@Composable
fun CreatedCharacterItem(character : CreatedCharacter){
    val toggle = remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = character.name!!,
            fontSize = 26.sp,
        )

        Surface(
            onClick = {
                toggle.value = !toggle.value
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                if(!toggle.value){
                    CharacterInfo(character)
                } else {
                    CharacterDescription(character.description!!)
                }
            }
        }

        Text("created: ${character.created}")
    }
}

@Composable
fun CharacterInfo(character: CreatedCharacter){
    Text("Info", fontSize = 24.sp)
    Text(
        text = "sex: ${character.gender!!}",
        fontSize = 20.sp
    )
    Text(
        text = "species: ${character.species}",
        fontSize = 20.sp
    )
    Text(
        text = "originating from: ${character.origin}"
    )
}

@Composable
fun CharacterDescription(description : String){
    Text("Description", fontSize = 24.sp)
    Text(
        text = description
    )
}
