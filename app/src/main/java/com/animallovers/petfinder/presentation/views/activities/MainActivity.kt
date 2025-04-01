package com.animallovers.petfinder.presentation.views.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.viewmodel.GetAnimalsViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel
import com.animallovers.petfinder.ui.theme.PetFinderTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PetFinderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    GetTokenChecker()
                }
            }
        }
    }
}


@Composable
fun GetTokenChecker(modifier: Modifier = Modifier) {

    val tokenViewModel = hiltViewModel<TokenViewModel>()
    val animalViewModel = hiltViewModel<GetAnimalsViewModel>()

    val tokenResults = tokenViewModel.tokenResultFlow.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {

        var token by remember { mutableStateOf("") }

        Button(
            onClick = {
            tokenViewModel.getAccessToken(
                TokenRequest(
                    clientId = "mdA9G4G4KscVDLV9Yyz4ItYON7vOz9wwBUYj4oxn7SF7QHmqd9",
                    clientSecret = "i13xhZJLsNAWeycGwnvj06RUEL9iTdpeMsjy9vCD"
                )
            )
        }) {
            Text("Request Access Token Check")
        }


        when (val tokenData = tokenResults.value) {
            is PetFinderResult.Loading -> ""
            is PetFinderResult.Success -> {
                 token = tokenData.data.accessToken
            }
            is PetFinderResult.Failure -> {
                Log.d("PetREsultFailure", tokenData.errorMessage)
            }
            else -> ""
        }


        Button(
            onClick = {
                animalViewModel.getAnimals(token)
                Log.d("LoginToken","Token === $token")
            }
        ) {
            Text("Get Animals Check")
        }
    }

}