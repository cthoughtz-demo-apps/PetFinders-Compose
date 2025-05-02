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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.animallovers.petfinder.R
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
import com.animallovers.petfinder.presentation.util.Constants.PLACE_HOLDER
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.viewmodel.OrganizationsViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel


private const val TAG = "OrgsPage"

@Composable
fun OrgsPage(
    getOrganizationsViewModel: OrganizationsViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel(),
) {

    val orgResult =
        getOrganizationsViewModel.getOrganizationsResultFlow.collectAsStateWithLifecycle()

    tokenViewModel.authToken?.let {
        getOrganizationsViewModel.getOrganizations(it)
        GetData(orgResult)
    }
}

@Composable
fun GetData(orgResult: State<PetFinderResult<GetOrganizationsResponse>>) {

    when (val orgData = orgResult.value) {
        is PetFinderResult.Failure -> {
            Log.d(TAG, "GetData: ${orgData.errorMessage}")
        }

        is PetFinderResult.Loading -> CircularProgressIndicator()
        is PetFinderResult.Success -> {
            ShowList(orgData.data)
        }

        else -> Log.d(TAG, "GetData: State None")
    }
}


@Composable
fun ShowList(data: GetOrganizationsResponse) {
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
            text = "Organizations",
            fontSize = 18.sp,
            fontWeight = FontWeight.ExtraBold,
            fontFamily = FontFamily(Font(R.font.dmsans)),
            color = colorResource(R.color.off_black_text)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 20.dp, start = 10.dp, end = 10.dp)
                .background(color = Color.Transparent)
        ) {
            data.organizations?.let {
                items(items = it) { organizations ->
                    DisplayOrganizationsItemData(organizations)
//                    DisplayItemData(
//                        image = if (organizations?.photos?.size != 0) {
//                            organizations?.photos?.get(0)?.small
//                                ?: PLACE_HOLDER
//                        } else {
//                            PLACE_HOLDER
//                        },
//                        name = organizations?.name ?: "N/A",
//                        age = organizations?.age ?: "N/A",
//                        gender = organizations?.gender ?: "N/A",
//                        adoptable = organizations?.status ?: "N/A",
//                        id = organizations?.id ?: 0,
//                        organizations
//                    )
                }
            }
        }
    }
}

@Composable
fun DisplayOrganizationsItemData(organizations: GetOrganizationsResponse.Organization?) {

    Card(
        modifier = Modifier
            //.size(150.dp, 15.dp)
            .clickable {
                //Log.d(TAG, "DisplayItemData: $id")
                //navigate.navigate(Pages.HomePetDetails.route + "/$id")
            }
            .clip(RoundedCornerShape(20.dp))
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = if (organizations?.photos?.size != 0) {
                        organizations?.photos?.get(0)?.large ?: PLACE_HOLDER
                    } else {
                        PLACE_HOLDER
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(10.dp)
                    .size(height = 170.dp, width = 148.dp)
            )

            Text(
                text = organizations?.name ?: "N/A",
                fontFamily = FontFamily(Font(R.font.dmsans)),
                fontSize = 17.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = organizations?.phone ?: "N/A",
                modifier = Modifier.padding(10.dp),
                color = colorResource(R.color.light_grey_text),
                fontSize = 11.sp
            )
        }
    }
}

@Preview
@Composable
fun DisplayOrganizationsItemDataPreview(modifier: Modifier = Modifier) {
    DisplayOrganizationsItemData(organizations = GetOrganizationsResponse.Organization())
}