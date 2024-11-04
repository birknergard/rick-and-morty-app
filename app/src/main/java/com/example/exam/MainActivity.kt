package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam.data.Repository
import com.example.exam.screens.*
import com.example.exam.ui.theme.ExamTheme
import com.example.exam.viewModels.Screen01ViewModel
import com.example.exam.viewModels.Screen03ViewModel
import kotlinx.serialization.Serializable

@Serializable
object Screen01

@Serializable
object Screen02

@Serializable
object Screen03

@Serializable
object Screen04


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Repository.initDB(applicationContext)

        enableEdgeToEdge()
        setContent {
            ExamTheme {
                val nav = rememberNavController()
                NavHost(startDestination = Screen03, navController = nav){
                    composable<Screen01> {
                        Screen01(Screen01ViewModel())
                    }
                    composable<Screen02> {

                    }
                    composable<Screen03> {
                        Screen03(Screen03ViewModel())
                    }
                    composable<Screen04> {

                    }
                }

            }
        }
    }
}