package com.example.exam.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.exam.Screen01
import com.example.exam.Screen02
import com.example.exam.Screen03
import com.example.exam.Screen04

class NavBarViewModel : ViewModel() {
    // Ive made one function for each route because i cant (as far as i know) pass serializable objects via. parameters.
    fun goToScreen01(navController: NavController){
        navController.navigate(Screen01){
            launchSingleTop = true // This line makes it so there is only one instance of the screen 1 at a timne.
            restoreState = true // When route is navigated back to, restores the state of the route from the last time it was accessed.
        }
    }
    fun goToScreen02(navController: NavController){
        navController.navigate(Screen02){
            launchSingleTop = true
        }
    }
    fun goToScreen03(navController: NavController){
        navController.navigate(Screen03){
            launchSingleTop = true
        }
    }
    fun goToScreen04(navController: NavController){
        navController.navigate(Screen04){
            launchSingleTop = true
            restoreState = true
        }
    }
}