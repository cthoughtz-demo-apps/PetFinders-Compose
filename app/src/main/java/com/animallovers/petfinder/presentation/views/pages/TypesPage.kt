package com.animallovers.petfinder.presentation.views.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.animallovers.petfinder.R

@Composable
fun TypesPages(modifier: Modifier = Modifier) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    0.0f to colorResource(R.color.light_purple),
                    0.8f to Color.White,
                    start = Offset(Float.POSITIVE_INFINITY, 0f),
                    end = Offset(0f, Float.POSITIVE_INFINITY)
                )
            )
            .padding(top = 100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Animal Types",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(Font(R.font.dmsans)),
            color = colorResource(R.color.off_black_text)
        )

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            AnimalCard(
                image = R.drawable.dog_face,
                name = "Dog"
            )
            AnimalCard(
                image = R.drawable.cat_face,
                name = "Cat"
            )
        }

        Row(
            Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            AnimalCard(
                image = R.drawable.bird_face,
                name = "Bird"
            )
            AnimalCard(
                image = R.drawable.rabbit_face,
                name = "Rabbit"
            )
        }
    }
}

@Composable
fun AnimalCard(
    image: Int,
    name: String
) {

    Card(
        modifier = Modifier
            .height(149.dp)
            .width(156.dp)
            .clip(RoundedCornerShape(15.dp))
            .padding(5.dp),
        backgroundColor = Color.White
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {


            Image(
                painterResource(image),
                contentDescription = null
            )

            Text(
                name,
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
fun AnimalCardPreview(modifier: Modifier = Modifier) {
    AnimalCard(
        image = 0,
        name = "Dog"
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun TypesPagesPreview(modifier: Modifier = Modifier) {
    TypesPages()
}