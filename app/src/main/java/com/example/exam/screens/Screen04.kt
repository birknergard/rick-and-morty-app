package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.exam.dataClasses.Episode
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen04ViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asStateFlow
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
    val episodes = vm.filteredList.collectAsState()

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
        if(episodes.value.isEmpty()){
            item {
                Text("Episodelist is empty.")
            }
        }
        items(episodes.value){ episode ->
            EpisodeDisplay(episode, vm)
        }
        item {
            Surface(
                onClick = {

                },

            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.ArrowForward),
                    contentDescription = "Arrow"
                )
            }
        }
    }
}

@Composable
fun EpisodeDisplay(episode: Episode, viewModel: Screen04ViewModel){
    val toggle = episode.toggle.collectAsState()
    episode.data.getSeasonAndEpisode()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(defaultPadding)
            .height(IntrinsicSize.Max),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = "${episode.data.getSeasonAndEpisode().second} - ${episode.data.name}",
            fontSize = 20.sp
        )
        Surface(
            onClick = {
                viewModel.toggleCharacterList(episode)
                Log.d("Screen04", "Episode character list in screen04 composable: ${episode.appearingCharacters.value}")
            },
            modifier = Modifier
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
        ) {
            if(toggle.value){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .padding(top = 30.dp)
                    ,
                    columns = GridCells.FixedSize(170.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center

                ){
                    items(episode.appearingCharacters.value){ character ->
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly

                        ) {
                            Text(
                                text = character.name!!,
                                fontSize = 12.sp
                            )
                            AsyncImage(
                                modifier = Modifier.size(150.dp).clip(RoundedCornerShape(10.dp)),
                                model = character.image,
                                contentDescription = "image of character",
                                placeholder = rememberVectorPainter(Icons.Default.Person)
                            )
                            Spacer(Modifier.height(15.dp))
                        }
                    }

                }
            } else {

            }
        }
    }

}
