package com.animallovers.petfinder.presentation.views.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.presentation.navigation.Pages
import com.animallovers.petfinder.presentation.navigation.SetupNavGraph
import com.animallovers.petfinder.presentation.util.PetFinderResult
import com.animallovers.petfinder.presentation.viewmodel.GetAnimalsViewModel
import com.animallovers.petfinder.presentation.viewmodel.SplashViewModel
import com.animallovers.petfinder.presentation.viewmodel.TokenViewModel
import com.animallovers.petfinder.ui.theme.PetFinderTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        installSplashScreen().setKeepOnScreenCondition {
            !splashViewModel.isLoading.value
        }

        enableEdgeToEdge()

        setContent {
            PetFinderTheme {
                Surface(modifier = Modifier.fillMaxSize()) {

                    val screen by splashViewModel.startDestination
                    val navController = rememberNavController()
                    SetupNavGraph(navController, startDestination = screen)
                }
            }
        }
    }
}


@Composable
fun GetTokenChecker() {

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
                Log.d("PetResultFailure", tokenData.errorMessage)
            }

            else -> ""
        }


        Button(
            onClick = {
                animalViewModel.getAnimals(token)
                Log.d("LoginToken", "Token === $token")
            }
        ) {
            Text("Get Animals Check")
        }
    }

}