package com.animallovers.petfinder.presentation.views.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.animallovers.petfinder.R
import com.animallovers.petfinder.presentation.navigation.BottomNavigationPages

@Composable
fun HomePage() {

    val items = listOf(
        BottomNavigationPages.HomePet,
        BottomNavigationPages.Types,
        BottomNavigationPages.Orgs,
    )

    var currentRoute by remember { mutableStateOf(BottomNavigationPages.HomePet.route) }
    val navController = rememberNavController()
    val bottomPaddingLayout = if (isThreeButtonNavSystem()) 48.dp else 0.dp

    Scaffold(
        bottomBar = {

            Box(
                modifier = Modifier.background(color = Color.White)
            ) {
                BottomNavigation(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            top = 20.dp,
                            bottom = bottomPaddingLayout + 20.dp,
                            start = 10.dp,
                            end = 10.dp
                        )
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.line_color_grey),
                            shape = RoundedCornerShape(
                                topStart = 8.dp,
                                topEnd = 8.dp,
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            )
                        )
                        .padding(10.dp),
                    elevation = 0.dp
                    ,
                    backgroundColor = Color.White,
                    // contentColor = Color.Green
                ) {
                    items.forEach { page ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(page.icon),
                                    contentDescription = null
                                )
                            },
                            label = { Text(page.label) },
                            selected = currentRoute == page.route,
                            onClick = {
                                navController.navigate(page.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true

                                    restoreState = true
                                }
                                currentRoute = page.route
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavigationPages.HomePet.route,
            modifier = Modifier.padding(innerPadding),

            ) {
            composable(BottomNavigationPages.HomePet.route) { HomePetPage() }
            composable(BottomNavigationPages.Types.route) { TypesPages() }
            composable(BottomNavigationPages.Orgs.route) { OrgsPage() }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePageGesturePreview(modifier: Modifier = Modifier) {
    HomePage()
}


@Preview(
    showBackground = true,
    device = "spec:parent=pixel_5,navigation=buttons",
    showSystemUi = true
)
@Composable
fun HomePageButtonsPreview(modifier: Modifier = Modifier) {
    HomePage()
}
