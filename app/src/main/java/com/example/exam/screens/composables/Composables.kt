package com.example.exam.screens.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exam.Screen01
import com.example.exam.Screen02
import com.example.exam.Screen03
import com.example.exam.Screen04
import com.example.exam.dataClasses.Character
import com.example.exam.dataClasses.Location
import com.example.exam.screens.Item
import com.example.exam.viewModels.Screen04ViewModel


@Composable
fun UITemplate(
    nav: NavController,
    navUIState : List<Boolean>,
    screenComposable: @Composable () -> Unit
){
    Column (modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .padding(vertical = 40.dp)
        .padding(top = 5.dp)
        ,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Spacer(
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth().background(color = Color.Gray)
                    .padding(vertical = 2.dp)
            )

            screenComposable()
        }

        NavBar(navUIState, nav)

    }
}

@Composable
fun NavBar(booleanList : List<Boolean>, nav: NavController) {
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
            NavItem(
                label = "View",
                isActive = booleanList[0],
                onClick = {
                    nav.navigate(Screen01){
                        launchSingleTop = true
                        popUpTo(nav.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )

            NavItem(
                label = "Your",
                isActive = booleanList[1],
                onClick = {
                    nav.navigate(Screen02){
                        launchSingleTop = true
                        popUpTo(nav.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
            NavItem(
                label = "Create",
                isActive = booleanList[2],
                onClick = {
                    nav.navigate(Screen03){
                        launchSingleTop = true
                        popUpTo(nav.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )
            NavItem(
                label = "Episode",
                isActive = booleanList[3],
                onClick = {
                    nav.navigate(Screen04){
                        launchSingleTop = true
                        popUpTo(nav.graph.startDestinationId){
                            saveState = true
                        }
                        restoreState = true
                    }
                }
            )

        }
    }
}

@Composable
fun NavItem(label : String, isActive : Boolean, onClick : () -> Unit){
    Surface(
        onClick = {
            if(!isActive){
                onClick()
            }
            // Doesnt do anything if page is current
        },
        modifier = Modifier
            .width(105.dp)
            .fillMaxHeight()
            .padding(vertical = 8.dp, horizontal = 2.dp)
        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 2.dp, color = Color.Magenta, shape = RectangleShape)
                .background(if (isActive) Color.Magenta else Color.White),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = label,
                color = if (isActive) Color.White else Color.Magenta,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
