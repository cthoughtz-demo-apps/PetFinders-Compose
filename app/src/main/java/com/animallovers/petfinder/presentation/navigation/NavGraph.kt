package com.animallovers.petfinder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.animallovers.petfinder.presentation.views.pages.WelcomePage

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = Pages.Welcome.route) {
            WelcomePage(navController = navController)
        }

        composable(route = Pages.Home.route) {
            //
        }
    }
}