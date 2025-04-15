package com.animallovers.petfinder.presentation.navigation

import com.animallovers.petfinder.R

sealed class BottomNavigationPages(
    val route: String,
    val label: String,
    val icon: Int
) {
    object HomePet : BottomNavigationPages("homePet", "Pets", R.drawable.pet_home)
    object Types : BottomNavigationPages("types", "Types", R.drawable.pet)
    object Orgs : BottomNavigationPages("orgs", "Orgs", R.drawable.building)
}