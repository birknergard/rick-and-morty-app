package com.example.exam.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun NavBar() {
    Column {
        Spacer(modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(color = Color.Gray))

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavItem("View")
            NavItem("Your")
            NavItem("Create")
            NavItem("Episodes")
        }
    }
}

@Composable
fun NavItem(label : String){
    Surface(
        onClick = {

        },
        modifier = Modifier
            .width(105.dp)
            .fillMaxHeight()
            .padding(5.dp)
        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
            contentAlignment = Alignment.Center
        ){
            Text(label)
        }
    }
}