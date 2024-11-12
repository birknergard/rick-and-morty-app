package com.example.exam.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.exam.screens.composables.NavBar
import com.example.exam.viewModels.Screen04ViewModel


// UI variables
private val titleBarHeight = 30.dp
private val componentHeight = 700.dp
private val defaultVerticalPadding = 15.dp

@Composable
fun Screen04(vm : Screen04ViewModel){
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(vertical = defaultVerticalPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Episodes",
            fontSize = 24.sp
        )
        Spacer(Modifier.height(2.dp).fillMaxWidth().background(Color.Gray))
    }
}

