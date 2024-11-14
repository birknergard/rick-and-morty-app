package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.ArrowBack
import androidx.compose.material.icons.automirrored.sharp.ArrowForward
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.text.style.TextAlign
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
private val componentHeight = 717.dp
private val defaultVerticalPadding = 15.dp
private val defaultPadding = 10.dp
private val arrowSize = 35.dp
private val textSize = 16.sp
private val titleTextSize = 24.sp

@Composable
fun Screen04(vm : Screen04ViewModel){

    LaunchedEffect(Unit) {
        vm.initialize()
    }

    val episodes = vm.filteredList.collectAsState()
    val season = vm.selectedSeason.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = defaultVerticalPadding)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(
                onClick = {
                    vm.selectPreviousSeason()
                }) {
                Icon(
                    modifier = Modifier
                        .size(arrowSize),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.ArrowBack),
                    contentDescription = "Arrow"
                )
            }
            Text(
                text = "Season ${season.value}",
                fontSize = 24.sp,
            )
            Surface(
                onClick = {
                    vm.selectNextSeason()
                }) {
                Icon(
                    modifier = Modifier
                        .size(arrowSize),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.ArrowForward),
                    contentDescription = "Arrow"
                )
            }
        }
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
    }
}

@Composable
fun EpisodeDisplay(episode: Episode, viewModel: Screen04ViewModel){
    val toggle = episode.toggle.collectAsState()
    episode.data.getSeasonAndEpisode()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .padding(vertical = defaultVerticalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .width(330.dp),
            text = "${episode.data.getSeasonAndEpisode().second} - \"${episode.data.name}\"",
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
        Surface(
            onClick = {
                viewModel.toggleCharacterList(episode)
                Log.d("Screen04", "Episode character list in screen04 composable: ${episode.appearingCharacters.value}")
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .border(width = 2.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
        ) {
            if(toggle.value){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .padding(top = defaultPadding)
                    ,
                    columns = GridCells.FixedSize(170.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center

                ){
                    items(episode.appearingCharacters.value){ character ->
                        Column(
                            verticalArrangement = Arrangement.SpaceEvenly,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) {
                            Text(
                                text = character.name!!,
                                fontSize = textSize
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(defaultPadding)
                    ) {
                        Text(
                            text = "Date aired: ${episode.data.air_date}",
                            fontSize = textSize
                        )
                        Spacer(Modifier.height(5.dp))
                        Text(
                            text = "Appearing characters: ${episode.data.getAppearingCharacters().size + 1}"
                        )
                    }
                    Icon(
                        modifier = Modifier.size(45.dp).padding(end = 10.dp),
                        painter = rememberVectorPainter(Icons.Outlined.Info),
                        contentDescription = "Icon"
                    )
                }
            }
        }
    }
}

