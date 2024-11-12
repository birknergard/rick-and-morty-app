package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.exam.dataClasses.Episode
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen04ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// UI Variables
private val componentHeight = 690.dp
private val defaultVerticalPadding = 15.dp
private val defaultPadding = 5.dp

@Composable
fun Screen04(vm : Screen04ViewModel){

    LaunchedEffect(Unit) {
        vm.initialize()
    }
    val episodes = vm.episodes.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = 15.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Episodes",
            fontSize = 24.sp
        )
        Spacer(Modifier.height(2.dp).fillMaxWidth().background(Color.Gray))
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(componentHeight),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(episodes.value){ episode ->
            EpisodeDisplay(episode, vm)
        }
    }
}

@Composable
fun EpisodeDisplay(episode: Episode, viewModel: Screen04ViewModel){
    val toggle = rememberSaveable() {
        mutableStateOf(false)
    }

    Surface(onClick = {
        if(episode.listIsEmpty()){
            viewModel.getCharactersByEpisode(episode)
        }
        toggle.value = !toggle.value
    }) {
        Text(
            text = episode.data.name,
            fontSize = 20.sp
        )
        if(toggle.value){
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ){
                items(episode.appearingCharacters.value){ character ->
                    Text(text = character.name!!)
                    AsyncImage(
                        model = character.image,
                        contentDescription = "image of character"
                    )
                }
            }
        }
    }

}
