package com.example.exam.composables

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Chair
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.PersonAdd
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.exam.Screen01
import com.example.exam.Screen02
import com.example.exam.Screen03
import com.example.exam.Screen04

val colorPalette : List<Color> = listOf(
    Color(red = 68, green = 40, blue = 29),
    Color(red = 228, green = 177, blue = 156),
    Color(red = 240, green = 225, blue = 74),
    Color(red = 151, green = 206, blue = 76),
    Color(red = 232, green = 154, blue = 199),
)

@Composable
fun UITemplate(
    nav: NavController,
    navUIState : List<Boolean>,
    screenComposable: @Composable () -> Unit
){
    Column (modifier = Modifier
        .fillMaxHeight()
        .fillMaxWidth()
        .background(colorPalette[3])
        .padding(vertical = 10.dp)
        .padding(top = 15.dp)
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
                    .height(2.dp)
                    .fillMaxWidth().background(color = colorPalette[0])
                    .padding(vertical = 2.dp)
            )

            screenComposable()
        }

        NavBar(navUIState, nav)

    }
}

@Composable
fun NavBar(booleanList : List<Boolean>, nav: NavController) {
    Column (
        Modifier
            .background(colorPalette[3])
    ){
        Spacer(modifier = Modifier
            .height(2.dp)
            .fillMaxWidth()
            .background(colorPalette[0])
        )

        Row(
            modifier = Modifier.fillMaxWidth()
                .height(80.dp)
            ,
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NavItem(
                icon = Icons.Rounded.FormatListNumbered,
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
                icon = Icons.Rounded.PersonSearch,
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
                icon = Icons.Rounded.PersonAdd,
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
                icon = Icons.Rounded.Chair,
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
fun NavItem(icon : ImageVector, isActive : Boolean, onClick : () -> Unit){
    Surface(
        onClick = {
            if(!isActive){
                onClick()
            }
        },
        modifier = Modifier
            .width(105.dp)
            .fillMaxHeight()
            .padding(vertical = 8.dp, horizontal = 2.dp)
        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .border(width = 1.dp, color = if(isActive) colorPalette[3] else colorPalette[0])
                .background(if (isActive) colorPalette[3] else colorPalette[2]),
            contentAlignment = Alignment.Center
        ){
            Icon(
                modifier = Modifier.size(50.dp),
                painter = rememberVectorPainter(icon),
                tint = if (isActive) colorPalette[0] else colorPalette[0],
                contentDescription = "Nav item icon"
            )
        }
    }
}
