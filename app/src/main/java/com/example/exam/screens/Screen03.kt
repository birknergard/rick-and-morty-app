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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exam.dataClasses.Location
import com.example.exam.viewModels.Screen03ViewModel

@Composable
fun Screen03(viewModel: Screen03ViewModel){

    LaunchedEffect(Unit) {
        viewModel.initializeLocationDatabase()
        viewModel.loadLocations()
    }

    val name = viewModel.name.collectAsState()
    val species = viewModel.species.collectAsState()
    val description = viewModel.description.collectAsState()


    // Column is set to 750 dp to fill out the template.
    // Could not be done automatically by any means.
    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA
    Column(
        modifier = Modifier
            .background(Color.White)
            .height(730.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Create your own character",
            modifier = Modifier.padding(vertical = 30.dp),
            fontSize = 25.sp
        )
        NameSelect(name.value, viewModel)
        GenderSelectionGrid(viewModel)
        OriginSelect(viewModel.getLocationList())
        SpeciesSelect(species.value, viewModel)
        Description(description.value, viewModel)
        Surface(onClick = {

        }){
            Row(){
                Text("Add Character")
                Icon(
                    painter = rememberVectorPainter(Icons.Default.Add),
                    contentDescription = "Add icon"
                )
            }
        }
    }
}

@Composable
fun NameSelect(name : String, viewModel: Screen03ViewModel){
    Text("Name")
    OutlinedTextField(
        value = name,
        onValueChange = { viewModel.setName(it) },
        label = { Text("Enter a name") }
    )
}

@Composable
fun GenderSelectionGrid(
    viewModel: Screen03ViewModel
){
    val options = viewModel.genderOptions
    val selection = viewModel.getSelectionToggleList()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text("Gender")
        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            SelectButton(options[0], selection, 0, viewModel)
            SelectButton(options[1], selection, 1, viewModel)
        }
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SelectButton(options[2], selection, 2, viewModel)
            SelectButton(options[3], selection, 3, viewModel)
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
        isToggled[thisItem] = !isToggled[thisItem] // Inverts the boolean  on click
        isToggled.indices.forEach { i -> if(i != thisItem) isToggled[i] = false } // Sets all other booleans to false, so you cant toggle more than one field
        viewModel.setGender(text.lowercase())
    },
        modifier = Modifier.padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .width(180.dp).height(100.dp)
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

@Composable
fun OriginSelect(
locationList : List<Location>
){
    val locationListToggle = remember { mutableStateOf(false) }
    val locationInput = rememberSaveable {mutableStateOf("")}
    val isFocused = remember { mutableStateOf(false) }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text("Origin")
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
        if(locationListToggle.value){
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
fun SpeciesSelect(species : String, viewModel: Screen03ViewModel){
    Text("Species")
    OutlinedTextField(
        value = species,
        onValueChange = {
            viewModel.setSpecies(it)
        },
        label = { Text("Species") }
    )
    Spacer(Modifier.padding(vertical = 15.dp))
}

@Composable
fun Description(description : String, viewModel: Screen03ViewModel){
    Text("Description")
    OutlinedTextField(
        value = description,
        onValueChange = {
            viewModel.setDesc(it)
        },
        label = { Text("Description") }
    )
}
