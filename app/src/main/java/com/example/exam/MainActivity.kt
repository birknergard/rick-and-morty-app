package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam.screens.Screen01
import com.example.exam.ui.theme.ExamTheme
import com.example.exam.viewModels.Screen01ViewModel
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
        enableEdgeToEdge()
        setContent {
            ExamTheme {
                val repo = Repository
                val vm = Screen01ViewModel(repo)

                val nav = rememberNavController()
                NavHost(startDestination = Screen01, navController = nav){
                    composable<Screen01> {
                        Screen01(vm)
                    }
                    composable<Screen02> {

                    }
                    composable<Screen03> {

                    }
                    composable<Screen04> {

                    }
                }

            }
        }
    }
}