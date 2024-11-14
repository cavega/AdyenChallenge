package com.adyen.android.assignment.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.adyen.android.assignment.astronomy.ScreenDestination
import com.adyen.android.assignment.astronomy.ui.list.AstronomyPhotosScreen
import com.adyen.android.assignment.astronomy.ui.theme.AdyenTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            AdyenTheme {
                AdyenApp()
            }
        }
    }

    @Composable
    fun AdyenApp() {
        navController = rememberNavController()
        NavHost(
            navController as NavHostController,
            startDestination = ScreenDestination.AstronomyPhotosDestination.route
        ) {
            composable(route = ScreenDestination.AstronomyPhotosDestination.route) {
                AstronomyPhotosScreen(navController)
            }
            // TODO: Add more destinations
        }
    }
}