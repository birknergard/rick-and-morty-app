package com.example.exam.composables

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Autorenew
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exam.viewModels.Screen03ViewModel

// UI variables
private val composableHeight = 710.dp
private val textBoxWidth = 350.dp
private val defaultVerticalPadding = 15.dp
private val defaultPadding = 5.dp
private val buttonWidth = 150.dp
private val buttonHeight = 50.dp

private val titleFontSize = 20.sp


@Composable
fun Screen03(viewModel: Screen03ViewModel){

    LaunchedEffect(Unit) {
        viewModel.initializeLocationDatabase()
        viewModel.loadLocations()
    }

    val name = viewModel.name.collectAsState()
    val species = viewModel.species.collectAsState()
    val description = viewModel.description.collectAsState()

    val allFieldsFilled = viewModel.allFieldsAreFilled.collectAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorPalette[2])
            .padding(top = defaultPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            ClearButton(viewModel)
            AddButton(viewModel)
        }

        if(!allFieldsFilled.value){
            Text(
                text = "All fields are not entered.",
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        Spacer(modifier = Modifier.height(1.dp).fillMaxWidth().background(colorPalette[0]))

    }
    Column(
        modifier = Modifier
            .height(composableHeight)
            .fillMaxWidth()
            .background(colorPalette[2])
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            modifier = Modifier.padding(horizontal = defaultPadding, vertical = defaultVerticalPadding + 10.dp),
            text = "Create your own Rick and Morty character",
            fontSize = 18.sp,
            color = colorPalette[0]
        )
        CharacterInputField(
            fieldValue = name.value,
            setter = {viewModel.setName(it)},
            maxInputLength = 50,
            title = "Name",
            labelText = "Enter a name"
        )
        GenderSelectionGrid(viewModel)
        OriginSelect(viewModel)
        CharacterInputField(
            fieldValue = species.value,
            setter = {viewModel.setSpecies(it)},
            maxInputLength = 50,
            title = "Species",
            labelText = "Enter a species"
        )
        Description(description.value, viewModel)
        Spacer(Modifier.height(30.dp)) // This is here incase of onscreen keyboard.
    }
}

@Composable
fun CharacterInputField(
    fieldValue : String,
    setter : (input : String) -> Unit,
    maxInputLength : Int,
    title : String,
    labelText : String
){
    Text(
        text = title,
        fontSize = titleFontSize,
        fontWeight = FontWeight.Bold,
        color = colorPalette[0]
    )
    OutlinedTextField(
        modifier = Modifier.width(textBoxWidth),
        value = fieldValue,
        onValueChange = {
            if(it.length <= maxInputLength){
                setter(it)
            }
        },
        label = { Text(
            text = labelText
        ) },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = colorPalette[2],
            unfocusedTextColor = colorPalette[0],
            unfocusedLabelColor = colorPalette[0],
            focusedContainerColor = colorPalette[2],
            focusedTextColor = colorPalette[0],
            focusedBorderColor = colorPalette[4],
            unfocusedBorderColor = colorPalette[0],
            focusedLabelColor = colorPalette[0]
        )
    )
    Spacer(Modifier.padding(vertical = 15.dp))
}

@Composable
fun AddButton(viewModel: Screen03ViewModel){
    Surface(
        modifier = Modifier
            .padding(vertical = defaultVerticalPadding)
            .width(buttonWidth)
            .height(buttonHeight)
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, colorPalette[0], RoundedCornerShape(10.dp)),
        color = colorPalette[4],
        onClick = {
            Log.d("Screen03", "Fields filled boolean = ${viewModel.allFieldsAreFilled.value}")
            viewModel.upload() // uploads character to database if all fields are filled
        }
    ) {
        Row(
            modifier = Modifier
                .height(buttonHeight)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "New",
                fontSize = 25.sp,
                color = Color.White
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                modifier = Modifier.height(30.dp).width(30.dp),
                painter = rememberVectorPainter(Icons.Default.Add),
                tint = Color.White,
                contentDescription = "Add icon"
            )
        }
    }
}

@Composable
fun ClearButton(viewModel: Screen03ViewModel){
    Surface(
        modifier = Modifier
            .padding(vertical = defaultVerticalPadding)
            .width(buttonWidth)
            .height(50.dp)
            .clip(RoundedCornerShape(10.dp))
            .border(width = 1.dp, colorPalette[0], RoundedCornerShape(10.dp)),
        color = colorPalette[1],
        onClick = {
            viewModel.clearAllFields()
        }
    ) {
        Row(
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Clear All",
                fontSize = 22.sp,
                color = Color.White
            )
            Spacer(Modifier.width(10.dp))
            Icon(
                modifier = Modifier.height(27.dp).width(27.dp),
                painter = rememberVectorPainter(Icons.Rounded.Autorenew),
                tint = Color.White,
                contentDescription = "clear icon"
            )
        }
    }
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
            .padding(vertical = defaultVerticalPadding)
            .background(colorPalette[2]),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Gender",
            fontSize = titleFontSize,
            fontWeight = FontWeight.Bold,
            color = colorPalette[0]
        )
        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            for(i in 0 .. 1){
                SelectButton(options[i], i, viewModel)
            }
        }

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            for(i in 2 .. 3){
                SelectButton(options[i], i, viewModel)
            }
        }
    }
}

@Composable
fun SelectButton(
    text : String,
    thisItem : Int,
    viewModel: Screen03ViewModel
){
    Surface(onClick = {
        viewModel.select(thisItem, text)
    },
        color = if(viewModel.getSelectionToggleList()[thisItem]) colorPalette[4] else colorPalette[2],
        modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier
                .width(145.dp).height(80.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(1.dp, colorPalette[0], RoundedCornerShape(10.dp))
            ,
            contentAlignment = Alignment.Center,
        ){
            Text(
                text = text,
                color = if(!viewModel.getSelectionToggleList()[thisItem]) colorPalette[0] else Color.White,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
fun OriginSelect(
    viewModel: Screen03ViewModel
) {
    val locationListToggle = rememberSaveable{ mutableStateOf(false) }
    val isFocused = rememberSaveable{ mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = defaultVerticalPadding),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Origin",
            fontSize = titleFontSize,
            fontWeight = FontWeight.Bold,
            color = colorPalette[0]
        )

        OutlinedTextField(
            modifier = Modifier
                .width(textBoxWidth)
                .onFocusChanged { focusState ->
                    isFocused.value = focusState.isFocused
                    locationListToggle.value = isFocused.value
                },
            value = viewModel.origin.collectAsState().value,
            onValueChange = {
                if (it.length <= 50) {
                    viewModel.setOrigin(it)
                }
            },
            label = { Text("Origin") },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = colorPalette[2],
                unfocusedTextColor = colorPalette[0],
                unfocusedLabelColor = colorPalette[0],
                focusedContainerColor = colorPalette[2],
                focusedTextColor = colorPalette[0],
                focusedBorderColor = colorPalette[4],
                unfocusedBorderColor = colorPalette[0],
                focusedLabelColor = colorPalette[0]
            )
        )

        if (locationListToggle.value) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .height(200.dp)
                    .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                    .background(colorPalette[4])
                    .padding(horizontal = 20.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                if(viewModel.getFilteredLocationList().isEmpty()){
                    item {
                        Text("No matching location could be found. Press add to create new location.")
                    }
                } else {
                    items(viewModel.getFilteredLocationList()) { location ->
                        Surface(
                            onClick = {
                                viewModel.setOrigin(location.name!!)
                                locationListToggle.value = false
                            },
                            modifier = Modifier.background(colorPalette[4])
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(colorPalette[4])
                                ,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "${viewModel.getFilteredLocationList().indexOf(location) + 1}. ${location.name!!}",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = colorPalette[0]
                                )
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = rememberVectorPainter(Icons.Rounded.Add),
                                    contentDescription = "Add icon",
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Description(description : String, viewModel: Screen03ViewModel){
    Text(
        text = "Description",
        fontSize = titleFontSize,
        fontWeight = FontWeight.Bold,
        color = colorPalette[0]
    )
    OutlinedTextField(
        modifier = Modifier.width(textBoxWidth),
        minLines = 3,
        value = description,
        onValueChange = {
            if(it.length < 140){
                viewModel.setDesc(it)
            }
        },
        label = { Text("Write a description of your character") },
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = colorPalette[2],
            unfocusedTextColor = colorPalette[0],
            unfocusedLabelColor = colorPalette[0],
            focusedContainerColor = colorPalette[2],
            focusedTextColor = colorPalette[0],
            focusedBorderColor = colorPalette[4],
            unfocusedBorderColor = colorPalette[0],
            focusedLabelColor = colorPalette[0]
        )
    )
}

