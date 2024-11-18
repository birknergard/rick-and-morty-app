package com.example.exam.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
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
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.exam.dataClasses.episode.Episode
import com.example.exam.viewModels.Screen04ViewModel

// UI Variables
private val componentHeight = 717.dp
private val defaultVerticalPadding = 15.dp
private val defaultPadding = 10.dp
private val arrowSize = 35.dp
private val textSize = 16.sp
private val titleTextSize = 22.sp

@Composable
fun Screen04(viewModel : Screen04ViewModel){

    LaunchedEffect(Unit) {
        viewModel.initialize()
    }

    val episodes = viewModel.filteredList.collectAsState()
    val season = viewModel.selectedSeason.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
            .background(colorPalette[2])
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
                .background(colorPalette[2])
                .padding(vertical = defaultVerticalPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Surface(
                onClick = {
                    viewModel.selectPreviousSeason()
                },
                color = colorPalette[2]
                ) {
                Icon(
                    modifier = Modifier
                        .size(arrowSize)
                        .background(colorPalette[2]),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.ArrowBack),
                    contentDescription = "Arrow",
                    tint = colorPalette[0]
                )
            }
            Text(
                text = "Season ${season.value}",
                fontSize = titleTextSize,
            )
            Surface(
                onClick = {
                    viewModel.selectNextSeason()
                }) {
                Icon(
                    modifier = Modifier
                        .size(arrowSize)
                        .background(colorPalette[2]),
                    painter = rememberVectorPainter(Icons.AutoMirrored.Sharp.ArrowForward),
                    contentDescription = "Arrow",
                    tint = colorPalette[0]
                )
            }
        }
        Spacer(Modifier.background(colorPalette[0]).height(1.dp).fillMaxWidth())
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height(componentHeight)
            .background(colorPalette[2]),
        verticalArrangement = Arrangement.spacedBy(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if(episodes.value.isEmpty()){
            item {
                Text(
                    modifier = Modifier.padding(50.dp),
                    text = "LOADING ...",
                    fontSize = titleTextSize
                )
            }
        }
        items(episodes.value){ episode ->
            EpisodeDisplay(episode, viewModel)
        }
    }
}

@Composable
fun EpisodeDisplay(episode: Episode, viewModel: Screen04ViewModel){

    val toggle = episode.toggle.collectAsState()

    LaunchedEffect(Unit) {
        episode.data.getSeasonAndEpisode()
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 100.dp)
            .background(colorPalette[2])
            .padding(vertical = defaultVerticalPadding)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            modifier = Modifier
                .padding(bottom = 5.dp)
                .width(370.dp),
            text = "Episode ${episode.data.getSeasonAndEpisode().second} - \"${episode.data.name}\"",
            fontSize = titleTextSize,
            textAlign = TextAlign.Center
        )
        Surface(
            onClick = {
                viewModel.toggleCharacterList(episode)
            //    Log.d("Screen04", "Episode character list in screen04 composable: ${episode.appearingCharacters.value}")
            },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .clip(RoundedCornerShape(10.dp))
                .border(width = 2.dp, color = colorPalette[0], shape = RoundedCornerShape(10.dp))
        ) {
            if(toggle.value){
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .height(500.dp)
                        .background(colorPalette[4])
                        .padding(top = defaultPadding),
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
                                modifier = Modifier
                                    .size(150.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .border(width = 1.dp, color = colorPalette[0], shape = RoundedCornerShape(10.dp)),
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
                    modifier = Modifier.background(colorPalette[4]),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(defaultPadding)
                    ) {
                        Text(
                            text = "Date aired: ${episode.data.airDate}",
                            fontSize = textSize
                        )

                        Spacer(Modifier.height(5.dp))

                        Text(
                            text = "Appearing characters: ${episode.data.getAppearingCharacters().size + 1}",
                            fontSize = textSize
                        )
                    }

                    Icon(
                        modifier = Modifier.size(45.dp).padding(end = 10.dp),
                        painter = rememberVectorPainter(Icons.Outlined.ExpandMore),
                        contentDescription = "Icon"
                    )
                }
            }
        }
    }
}

