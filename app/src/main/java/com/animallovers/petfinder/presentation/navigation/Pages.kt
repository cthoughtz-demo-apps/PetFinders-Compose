package com.animallovers.petfinder.presentation.navigation

sealed class Pages(val route: String) {
    object Welcome : Pages(route = "welcome_page")
    object Home : Pages(route = "home_page")
    object Splash : Pages(route = "splash_page")
    object HomePetDetails : Pages(route = "home_pet_detail")
    object OrgPetDetails : Pages(route = "org_detail")
}