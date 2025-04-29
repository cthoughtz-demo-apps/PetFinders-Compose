package com.animallovers.petfinder.presentation.views.pages

import android.content.DialogInterface.OnShowListener
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animallovers.petfinder.R
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
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

    when(val orgData = orgResult.value) {
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
    }
}