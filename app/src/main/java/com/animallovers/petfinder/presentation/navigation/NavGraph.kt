package com.animallovers.petfinder.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.animallovers.petfinder.presentation.views.pages.HomePage
import com.animallovers.petfinder.presentation.views.pages.HomePetDetailsPage
import com.animallovers.petfinder.presentation.views.pages.SplashPage
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
            HomePage(navController = navController)
        }

        composable(route = Pages.Splash.route) {
            SplashPage()
        }

        composable(
            route = Pages.HomePetDetails.route + "/{animalId}",
            arguments = listOf(navArgument("animalId"){type = NavType.IntType})
        ) { backStackEntry ->
            val animalId = backStackEntry.arguments?.getInt("animalId") ?: 0
            HomePetDetailsPage(animalId)
        }
    }
}