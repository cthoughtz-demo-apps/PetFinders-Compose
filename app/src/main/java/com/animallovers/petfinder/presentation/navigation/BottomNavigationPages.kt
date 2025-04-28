package com.animallovers.petfinder.presentation.navigation

sealed class BottomNavigationPages(
    val route: String,
    val label: String
) {
    object HomePet : BottomNavigationPages("homePet", "Pets")
    object Types : BottomNavigationPages("types", "Types")
    object Orgs : BottomNavigationPages("orgs", "Orgs")
}