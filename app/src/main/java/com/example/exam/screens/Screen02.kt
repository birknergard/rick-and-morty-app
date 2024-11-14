package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.AddCircle
import androidx.compose.material.icons.rounded.Add
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.screens.composables.NavBar
import com.example.exam.screens.composables.colorPalette
import com.example.exam.viewModels.Screen02ViewModel

private val componentHeight = 795.dp
private val fontSizeNormal = 16.sp
private val fontSizeTitle = 22.sp

@Composable
fun Screen02(vm : Screen02ViewModel){
    LaunchedEffect(Unit) {
        vm.initialize()
    }
    val characters = vm.characters.collectAsState()

    if(vm.hasCreatedCharacters()){
        LazyColumn(
            modifier = Modifier.height(componentHeight)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(characters.value){ character ->
                Spacer(Modifier.height(25.dp))
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
                fontSize = fontSizeTitle,
                textAlign = TextAlign.Center,
                color = Color.Red
            )
            Spacer(Modifier.padding(5.dp))
            Text(
                text = "Go to \"Create\" screen to make characters.",
                fontSize = fontSizeNormal,
                textAlign = TextAlign.Center
            )
        }
    }

}

@Composable
fun CreatedCharacterItem(character : CreatedCharacter){
    val toggle = remember { mutableStateOf(false) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = character.name!!,
            fontSize = fontSizeTitle,
            color = colorPalette[0]
        )

        Column(
            modifier = Modifier
                .background(colorPalette[4])
                .clip(RoundedCornerShape(10.dp))
                .border(width = 2.dp, color = colorPalette[0], shape = RoundedCornerShape(10.dp))
                .padding(15.dp)
        ) {
            Surface(
                modifier = Modifier.background(colorPalette[4]).clip(RoundedCornerShape(10.dp)),
                onClick = {
                    toggle.value = !toggle.value
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(colorPalette[4]).clip(RoundedCornerShape(10.dp))
                    ,
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.Start
                ) {
                    if(!toggle.value){
                        CharacterInfo(character)
                    } else {
                        CharacterDescription(character.description!!)
                    }
                    Spacer(Modifier.height(2.dp).fillMaxWidth().background(colorPalette[0]))
                    Text(
                        text = "created: ${character.created}",
                        fontSize = fontSizeNormal,
                        color = colorPalette[0]
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterInfo(character: CreatedCharacter){
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ){
        Icon(
            modifier = Modifier.size(30.dp),
            painter = rememberVectorPainter(Icons.Rounded.Add),
            contentDescription = "Icon"
        )
    }
    Text(
        text = "sex: ${character.gender!!}",
        fontSize = fontSizeNormal
    )
    Text(
        text = "species: ${character.species}",
        fontSize = fontSizeNormal
    )
    Text(
        text = "originating from: ${character.origin}",
        fontSize = fontSizeNormal
    )
}

@Composable
fun CharacterDescription(description : String){
    Text(
        text = "Description",
        fontSize =  fontSizeTitle
    )
    Text(
        text = description,
        fontSize = fontSizeNormal
    )
}
