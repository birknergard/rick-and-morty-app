package com.example.exam.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.Circle
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage

import com.example.exam.dataClasses.character.Character
import com.example.exam.viewModels.Screen01ViewModel

// UI variables
private val componentHeight = 736.dp

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(viewModel: Screen01ViewModel){
    val characters = viewModel.characterList.collectAsState()
    val apiCallSuccessful = viewModel.apiCallSuccessful.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(colorPalette[2])
        ,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(vertical = 15.dp),
            text = "Rick and Morty Characters",
            fontSize = 24.sp,
            color = colorPalette[0]
        )
        Spacer(Modifier.height(2.dp).fillMaxWidth().background(colorPalette[0]))
    }

    if(apiCallSuccessful.value == null){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight)
                .background(colorPalette[2])
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Getting characters ...", fontSize = 20.sp)
        }

    } else if(apiCallSuccessful.value!!) {
        // Standard UI
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight)
                .background(colorPalette[2])
                .padding(horizontal = 10.dp)
            ,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.height(5.dp))
            }
            items(characters.value) { character ->
                Spacer(modifier = Modifier.height(10.dp))
                Item(character)
            }
            item {
                if(!viewModel.reachedMaxPage()){
                    Surface(
                        modifier = Modifier.padding(10.dp),
                        onClick = {
                            viewModel.page.value++
                            viewModel.updateCharacterList(viewModel.page.value)
                        }
                    ){
                        Image(
                            modifier = Modifier.size(50.dp).background(colorPalette[2]),
                            painter = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                            contentDescription = "Plus icon"
                        )
                    }
                } else {
                    Text("You've reached the end of the characterlist.")
                }
            }
        }

    } else {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(componentHeight)
                .background(colorPalette[2])
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Could not load characters.", fontSize = 20.sp)
        }
    }
}

@Composable
fun Item(character : Character){
   Row(
       modifier = Modifier
           .fillMaxWidth().height(170.dp)
           .clip(RoundedCornerShape(15.dp))
           .border(width = 2.dp, color = colorPalette[0], shape = RoundedCornerShape(15.dp))
           .background(color = colorPalette[1])
           .padding(end = 10.dp),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.Start
   ){
       Row {
           AsyncImage(
               modifier = Modifier
                   .size(170.dp)
                   .clip(RoundedCornerShape(topStart = 15.dp, bottomStart = 15.dp))
               ,
               model = character.image!!,
               placeholder = rememberVectorPainter(Icons.Default.Person),
               contentScale = ContentScale.Crop,
               contentDescription = "Image of character"
           )
           Spacer(Modifier.width(1.dp).fillMaxHeight().background(colorPalette[0]))
       }

       Column (
           modifier = Modifier
               .padding(start = 10.dp)
           ,
           horizontalAlignment = Alignment.Start
       ){
           Row {
               Text(
                   modifier = Modifier.width(150.dp),
                   text = character.name!!,
                   fontSize = 20.sp,
               )
               Icon(
                   painter = rememberVectorPainter(
                       when(character.status!!.lowercase()){
                           "unknown" -> Icons.Rounded.QuestionMark
                           else -> Icons.Rounded.Circle
                       } ),
                   contentDescription = "Status icon",
                   tint = when(character.status.lowercase()){
                       "alive" -> Color.Green
                       "dead" -> Color.Red
                       else -> Color.White
                   }
               )
           }
           Text(
               text = "${character.gender!!} - ${character.species}",
               fontSize = 16.sp
           )
       }
   }
   Spacer(modifier = Modifier.padding(10.dp).background(Color.White))
}
