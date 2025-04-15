package com.animallovers.petfinder.presentation.views.pages

import android.util.Log
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.viewmodel.GetAnimalsViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel

private const val TAG = "HomePetPage"

@Composable
fun HomePetPage(
    modifier: Modifier = Modifier,
    getAnimalsViewModel: GetAnimalsViewModel = hiltViewModel(),
    tokenViewModel: TokenViewModel = hiltViewModel()
) {


    val petResult = getAnimalsViewModel.getAnimalsResultFlow.collectAsStateWithLifecycle()

    getAnimalsViewModel.getAnimals(tokenViewModel.authToken ?: "No Token")

    GetData(petResult)

}

@Composable
fun GetData(petResult: State<PetFinderResult<GetAnimalsResponse>>) {
    when (val petData = petResult.value) {
        is PetFinderResult.Failure -> {}
        is PetFinderResult.Loading -> CircularProgressIndicator()
        is PetFinderResult.Success -> {
            Log.d(TAG, "GetData: ${petData.data}")
        }

        else -> Log.d(TAG, "GetData: Something Went Wrong")
    }
}
