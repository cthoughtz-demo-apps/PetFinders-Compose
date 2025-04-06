package com.animallovers.petfinder.presentation.views.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.animallovers.petfinder.R

@Composable
fun SplashPage() {

    val colors = listOf(Color.White, colorResource(R.color.light_purple))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.radialGradient(
                    colors = colors,
                    center = androidx.compose.ui.geometry.Offset.Unspecified,
                    radius = 2000f
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.dog_cat_logo),
            contentDescription = "petFinder Logo",
        )
    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SplashPagePreview(modifier: Modifier = Modifier) {
    SplashPage()
}