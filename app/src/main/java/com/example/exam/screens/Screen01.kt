package com.example.exam.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.exam.dataClasses.Character
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen01ViewModel
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

//@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Screen01(vm: Screen01ViewModel){
    // Setup
    val page = vm.page.collectAsState()
    val characters = vm.characterList.collectAsState()

    val apiCallSuccessful = vm.apiCallSuccessful.collectAsState()

    LaunchedEffect(Unit) {
        if(apiCallSuccessful.value == null){
            vm.updateCharacterList(page.value)
        }
        delay(2000)
        Log.e("IMAGEURL", characters.value[0].image!!)
    }

    if(apiCallSuccessful.value == null){
        Column(
            modifier = Modifier.fillMaxWidth().height(750.dp).background(Color.White),
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
                .height(750.dp)
        ) {
            items(characters.value) { character ->
                Item(character)
            }
        }

    } else {
        Column(
            modifier = Modifier.fillMaxWidth().height(750.dp),
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
           .fillMaxWidth().height(200.dp)
           .background(color = Color.White)
           .padding(horizontal = 10.dp, vertical = 10.dp),
       verticalAlignment = Alignment.CenterVertically,
       horizontalArrangement = Arrangement.SpaceBetween
   ){

       AsyncImage(
           modifier = Modifier.size(150.dp),
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
}