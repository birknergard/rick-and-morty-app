package com.example.exam.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastFilter
import androidx.navigation.NavController
import androidx.room.util.TableInfo
import com.example.exam.data.Repository
import com.example.exam.dataClasses.CreatedCharacter
import com.example.exam.dataClasses.Location
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen03ViewModel

@Composable
fun Screen03(viewModel: Screen03ViewModel){

    LaunchedEffect(Unit) {
        viewModel.initializeLocationDatabase()
        viewModel.loadLocations()
    }

    val createdCharacter = viewModel.createdCharacter.collectAsState()

    // Column is set to 750 dp to fill out the template.
    // Could not be done automatically by any means.
    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    Column(
        modifier = Modifier
            .background(Color.White).height(730.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Create your own character", fontSize = 25.sp)
        OutlinedTextField(
            value = createdCharacter.value.name!!,
            onValueChange = { createdCharacter.value.name = it
            },
            label = { Text("Enter a name") }
        )

        GenderSelectionGrid(
            options = viewModel.genderOptions,
            selection = viewModel.getSelectionToggleList(),
            createdCharacter = createdCharacter.value

        )

        LocationSelect(viewModel.getLocationList())
    }
}

@Composable
fun GenderSelectionGrid(
    options: List<String>,
    selection: SnapshotStateList<Boolean>,
    createdCharacter: CreatedCharacter
){

    LazyVerticalGrid(
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceEvenly,
        columns = GridCells.FixedSize(120.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        items(options){ option ->
            SelectButton(option, selection, options.indexOf(option), createdCharacter)
        }

    }
}

@Composable
fun LocationSelect(
locationList : List<Location>
){
    val focusRequester = remember { FocusRequester() }
    val locationInput = rememberSaveable {mutableStateOf("")}
    val isFocused = remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .focusRequester(focusRequester)
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                },
            value = locationInput.value,
            onValueChange = { locationInput.value = it },
            label = { Text("Origin") }
        )
        if(isFocused.value){
        LazyColumn (
            modifier = Modifier
                .width(250.dp).height(200.dp).padding(start = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
                items(locationList.filter {
                    it.name!!.lowercase().startsWith(
                        locationInput.value.lowercase()
                    )
                }){ location ->
                    Surface(
                        onClick = {
                            focusRequester.requestFocus()
                            locationInput.value = location.name!!
                        }
                    ) {
                        Text(
                            text =  location.name!!,
                            fontSize = 20.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SelectButton(
    text : String,
    isToggled : MutableList<Boolean>,
    thisItem : Int,
    character: CreatedCharacter
){
    Surface(onClick = {
        isToggled[thisItem] = !isToggled[thisItem] // Inverses the boolean  on click
        isToggled.indices.forEach { i -> if(i != thisItem) isToggled[i] = false } // Sets all other booleans to false, so you cant toggle more than one field
        character.gender = text.lowercase()
        Log.i("CreatedCharacter", character.gender.toString())
        }
    ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5.dp))
                    .background(if (!isToggled[thisItem]) Color.White else Color.Gray)
                    .border(1.dp, Color.Gray, RoundedCornerShape(5.dp))
                    .padding(vertical = 5.dp)
                ,
                contentAlignment = Alignment.Center,
            ){
                Text(text = text, color = if(!isToggled[thisItem]) Color.Black else Color.White)
            }
    }
}

