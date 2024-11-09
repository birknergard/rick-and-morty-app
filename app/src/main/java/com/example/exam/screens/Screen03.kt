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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
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
import androidx.lifecycle.viewmodel.compose.viewModel
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

    val name = viewModel.name.collectAsState()
    val gender = viewModel.gender.collectAsState()
    val origin = viewModel.origin.collectAsState()
    val species = viewModel.species.collectAsState()
    val description = viewModel.description.collectAsState()


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
            value = name.value,
            onValueChange = { viewModel.setName(it) },
            label = { Text("Enter a name") }
        )

        GenderSelectionGrid(viewModel = viewModel)

        LocationSelect(viewModel.getLocationList())

        OutlinedTextField(
            value = species.value,
            onValueChange = {
                viewModel.setSpecies(it)
            },
            label = { Text("Species") }
        )

        OutlinedTextField(
            value = description.value,
            onValueChange = {
                viewModel.setSpecies(it)
            },
            label = { Text("Description") }
        )
    }
}

@Composable
fun GenderSelectionGrid(
    viewModel: Screen03ViewModel
){
    val options = viewModel.genderOptions
    val selection = viewModel.getSelectionToggleList()

    LazyVerticalGrid(
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalArrangement = Arrangement.SpaceEvenly,
        columns = GridCells.FixedSize(120.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    ) {
        items(options){ option ->
            SelectButton(option, selection, options.indexOf(option), viewModel)
        }

    }
}

@Composable
fun LocationSelect(
locationList : List<Location>
){
    val locationListToggle = remember { mutableStateOf(false) }
    val locationInput = rememberSaveable {mutableStateOf("")}
    val isFocused = remember { mutableStateOf(false) }
    Column {
        OutlinedTextField(
            modifier = Modifier
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                    if(isFocused.value){
                        locationListToggle.value = true
                    }
                } ,
            value = locationInput.value,
            onValueChange = { locationInput.value = it },
            label = { Text("Origin") }
        )
        LazyColumn (
            modifier = Modifier
                .width(250.dp).height(200.dp).padding(start = 10.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ){
            if(locationListToggle.value){
                items(locationList.filter {
                    it.name!!.lowercase().startsWith(
                        locationInput.value.lowercase()
                    )
                }){ location ->
                    Surface(
                        onClick = {
                            locationInput.value = location.name!!
                            locationListToggle.value = false
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
    viewModel: Screen03ViewModel
){
    Surface(onClick = {
        isToggled[thisItem] = !isToggled[thisItem] // Inverses the boolean  on click
        isToggled.indices.forEach { i -> if(i != thisItem) isToggled[i] = false } // Sets all other booleans to false, so you cant toggle more than one field
        viewModel.setGender(text.lowercase())
    }) {
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

