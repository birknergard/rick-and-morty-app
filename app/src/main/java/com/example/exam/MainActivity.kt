package com.example.exam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam.backend.Repository
import com.example.exam.composables.*
import com.example.exam.composables.UITemplate
import com.example.exam.ui.theme.ExamTheme
import com.example.exam.viewModels.Screen01ViewModel
import com.example.exam.viewModels.Screen02ViewModel
import com.example.exam.viewModels.Screen03ViewModel
import com.example.exam.viewModels.Screen04ViewModel
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

        Repository.initializeDatabase(applicationContext)

        enableEdgeToEdge()
        setContent {
            ExamTheme {
                val viewModel01 = Screen01ViewModel()
                val viewModel02 = Screen02ViewModel()
                val viewModel03 = Screen03ViewModel()
                val viewModel04 = Screen04ViewModel()
                val nav = rememberNavController()
                NavHost(
                    startDestination = Screen03,
                    navController = nav,
                    // Removing default transitions
                    enterTransition = {EnterTransition.None},
                    exitTransition = {ExitTransition.None},
                    popExitTransition = {ExitTransition.None},
                    popEnterTransition = {EnterTransition.None}
                ){
                    composable<Screen01> {
                        UITemplate(
                            nav = nav,
                            navUIState = viewModel01.navUIState,
                            screenComposable = { Screen01(viewModel01) }
                        )
                    }
                    composable<Screen02> {
                        UITemplate(
                            nav = nav,
                            navUIState = viewModel02.navUIState,
                            screenComposable = { Screen02(viewModel02) }
                        )
                   }
                    composable<Screen03> {
                        UITemplate(
                            nav = nav,
                            viewModel03.navUIState,
                            screenComposable = { Screen03(viewModel03) }
                        )
                    }

                    composable<Screen04> {
                        UITemplate(
                            nav = nav,
                            navUIState = viewModel04.navUIState,
                            screenComposable = { Screen04(viewModel04) }
                        )
                    }
                }

            }
        }
    }
}