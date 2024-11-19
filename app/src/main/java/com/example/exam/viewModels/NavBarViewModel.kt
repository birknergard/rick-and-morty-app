package com.example.exam.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.exam.Screen01
import com.example.exam.Screen02
import com.example.exam.Screen03
import com.example.exam.Screen04

class NavBarViewModel : ViewModel() {
    // Ive made one function for each route because i cant (as far as i know) pass serializable objects via. parameters.
    // Function block code is made with inspiration from this thread:
    // https://stackoverflow.com/questions/72913451/how-to-save-and-restore-navigation-state-in-jetpack-compose
    fun goToScreen01(navController: NavController){
        navController.navigate(Screen01){
            launchSingleTop = true
            popUpTo(navController.graph.id) {
                saveState = true
            }
            restoreState = true
        }
    }
    fun goToScreen02(navController: NavController){
        navController.navigate(Screen02){
            launchSingleTop = true
            popUpTo(navController.graph.id) {
                saveState = true
            }
            restoreState = true
        }
    }
    fun goToScreen03(navController: NavController){
        navController.navigate(Screen03){
            launchSingleTop = true
            popUpTo(navController.graph.id) {
                saveState = true
            }
            restoreState = true
        }
    }
    fun goToScreen04(navController: NavController){
        navController.navigate(Screen04){
            launchSingleTop = true
            popUpTo(navController.graph.id) {
                saveState = true
            }
            restoreState = true
        }
    }
}