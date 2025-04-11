package com.animallovers.petfinder.presentation.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import com.animallovers.petfinder.R

sealed class BottomNavigationPages(
    val route: String,
    val label: String,
    val icon: Int
) {
    object HomePet: BottomNavigationPages("homePet", "Pets",  R.drawable.pet_home)
    object Types : BottomNavigationPages("types", "Types", R.drawable.pet)
    object Orgs : BottomNavigationPages("orgs", "Orgs", R.drawable.building)
}