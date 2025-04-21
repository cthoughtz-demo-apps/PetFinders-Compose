package com.animallovers.petfinder.presentation.views.pages

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomePetDetailsPage(animalId: Int) {

    Column(
        modifier = Modifier.fillMaxSize().background(color = Color.Magenta),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Toast.makeText(LocalContext.current, "Animal Id: $animalId", Toast.LENGTH_SHORT).show()
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomePetDetailsPagePreview(modifier: Modifier = Modifier) {
    HomePetDetailsPage(animalId = 35)
}
