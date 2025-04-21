package com.animallovers.petfinder.presentation.views.pages

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.animallovers.petfinder.R
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.presentation.navigation.Pages
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.viewmodel.GetAnimalsViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel

private const val TAG = "HomePetPage"

@Composable
fun HomePetPage(
    getAnimalsViewModel: GetAnimalsViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
    navigate: NavHostController
) {


    val petResult = getAnimalsViewModel.getAnimalsResultFlow.collectAsStateWithLifecycle()

    tokenViewModel.authToken?.let {
        getAnimalsViewModel.getAnimals(it)
        GetData(petResult, navigate)
    }
}

@Composable
fun GetData(petResult: State<PetFinderResult<GetAnimalsResponse>>, navigate: NavHostController) {
    when (val petData = petResult.value) {
        is PetFinderResult.Failure -> {
            Log.d(TAG, "GetData: ${petData.errorMessage}")
        }

        is PetFinderResult.Loading -> CircularProgressIndicator()
        is PetFinderResult.Success -> {
            Log.d(TAG, "GetData: ${petData.data}")
            ShowList(petData.data, navigate)
            // DisplayItemData()
        }

        else -> Log.d(TAG, "GetData: State None")
    }
}

@Composable
fun ShowList(data: GetAnimalsResponse, navigate: NavHostController) {

    //  val colors = listOf(Color.White, colorResource(R.color.light_purple))

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
    ) {
        Text(
            text = stringResource(R.string.near_by_pets),
            fontSize = 16.sp,
            color = colorResource(R.color.text_color_off_black),
            fontFamily = FontFamily(Font(R.font.dmsans)),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 50.dp, start = 20.dp, bottom = 20.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
                .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                .background(color = Color.Transparent)
        ) {
            data.animals?.let {
                items(items = it) { pets ->
                    DisplayItemData(
                        image = if (pets?.photos?.size != 0) {
                            pets?.photos?.get(0)?.small
                                ?: "https://st4.depositphotos.com/14953852/24787/v/450/depositphotos_247872612-stock-illustration-no-image-available-icon-vector.jpg"
                        } else {
                            "https://st4.depositphotos.com/14953852/24787/v/450/depositphotos_247872612-stock-illustration-no-image-available-icon-vector.jpg"
                        },
                        name = pets?.name ?: "N/A",
                        age = pets?.age ?: "N/A",
                        gender = pets?.gender ?: "N/A",
                        adoptable = pets?.status ?: "N/A",
                        id = pets?.id ?: 0,
                        navigate
                    )
                }
            }
        }
    }
}

@Composable
fun DisplayItemData(
    image: String = "",
    name: String = "",
    age: String = "",
    gender: String = "",
    adoptable: String = "",
    id: Int = 0,
    navigate: NavHostController
) {

    Card(
        modifier = Modifier
            .clickable {
                Log.d(TAG, "DisplayItemData: $id")
                navigate.navigate(Pages.HomePetDetails.route + "/$id")
            }
            .clip(RoundedCornerShape(20.dp))
    ) {

        ConstraintLayout(modifier = Modifier.padding(9.dp)) {

            val (
                animalImage,
                animalName,
                animalAge,
                animalGender,
                animalAdoptability
            ) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(model = image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(height = 170.dp, width = 148.dp)
                    .clip(
                        RoundedCornerShape(
                            33.dp
                        )
                    )
                    .constrainAs(animalImage) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
            )

            Text(
                text = name,
                fontSize = 12.sp,
                fontFamily = FontFamily(
                    Font(R.font.dmsans)
                ),
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 16.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .width(100.dp)
                    .constrainAs(animalName) {
                        top.linkTo(animalImage.bottom, margin = 8.dp)
                        start.linkTo(animalImage.start)
                    })

            Text(
                text = age,
                fontSize = 9.sp,
                lineHeight = 12.sp,
                color = colorResource(R.color.text_color_grey),
                modifier = Modifier.constrainAs(animalAge) {
                    top.linkTo(animalName.bottom)
                    start.linkTo(animalName.start)
                })

            Text(text = gender,
                fontSize = 9.sp,
                lineHeight = 12.sp,
                color = colorResource(R.color.text_color_grey),
                modifier = Modifier.constrainAs(animalGender) {
                    top.linkTo(animalImage.bottom, margin = 8.dp)
                    end.linkTo(animalImage.end)
                })

            Text(text = adoptable,
                fontSize = 9.sp,
                lineHeight = 12.sp,
                color = colorResource(R.color.text_color_grey),
                modifier = Modifier.constrainAs(animalAdoptability) {
                    top.linkTo(animalGender.bottom)
                    end.linkTo(animalGender.end)
                })
        }
    }

}


@Preview()
@Composable
fun DisplayPreview(modifier: Modifier = Modifier) {
    DisplayItemData(navigate = NavHostController(LocalContext.current))
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ShowListPreview(modifier: Modifier = Modifier) {
    ShowList(GetAnimalsResponse(), navigate = NavHostController(LocalContext.current))
}