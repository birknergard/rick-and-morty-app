package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Person
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

import com.example.exam.dataClasses.Character
import com.example.exam.viewModels.Screen01ViewModel
import kotlinx.coroutines.delay

// UI variables
private val componentHeight = 680.dp

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(vm: Screen01ViewModel){
    // Setup
    val characters = vm.characterList.collectAsState()

    val apiCallSuccessful = vm.apiCallSuccessful.collectAsState()

    LaunchedEffect(Unit) {
        if(apiCallSuccessful.value == null){
            vm.page.value = 1
            vm.updateCharacterList(vm.page.value)
        }
        delay(2000)
        Log.e("IMAGEURL", characters.value[0].image!!)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Rick and Morty Characters",
            fontSize = 24.sp
        )
        Spacer(Modifier.height(2.dp).fillMaxWidth().background(Color.Gray))
    }

    if(apiCallSuccessful.value == null){
        Column(
            modifier = Modifier.fillMaxWidth().height(componentHeight).background(Color.White),
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
                .padding(horizontal = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Spacer(modifier = Modifier.padding(top = 5.dp))
            }
            items(characters.value) { character ->
                Spacer(modifier = Modifier.padding(top = 10.dp))
                Item(character)
            }
            item {
                Surface(
                    modifier = Modifier.padding(20.dp),
                    onClick = {
                        vm.page.value++
                        vm.updateCharacterList(vm.page.value)
                    }
                ) {
                    Image(
                        modifier = Modifier.height(30.dp).width(30.dp),
                        painter = rememberVectorPainter(Icons.Default.KeyboardArrowDown),
                        contentDescription = "Plus icon"
                    )
                }
            }
        }

    } else {
        Column(
            modifier = Modifier.fillMaxWidth().height(componentHeight),
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
           .border(width = 2.dp,
       color = when (character.status) {
           "Alive" -> Color.Green
           "unknown" -> Color.Gray
           else -> Color.Red
       },
       shape = RoundedCornerShape(10.dp))

           .background(color = Color.White)
           .padding(horizontal = 10.dp, vertical = 10.dp),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween
   ){
       AsyncImage(
           modifier = Modifier
               .size(150.dp)
               .clip(RoundedCornerShape(10.dp)),
           model = character.image!!,
           placeholder = rememberVectorPainter(Icons.Default.Person),
           contentScale = ContentScale.Crop,
           contentDescription = "Image of character"
       )

       Spacer(modifier = Modifier.padding(10.dp))

       Column {
           Text(
               text = character.name!!,
               fontSize = 20.sp,
           )
           Text(
               text = character.gender!!,
               fontSize = 16.sp
           )
       }
   }
   Spacer(modifier = Modifier.padding(10.dp).background(Color.White))
}
