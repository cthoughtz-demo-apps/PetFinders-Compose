package com.animallovers.petfinder.presentation.views.activities

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.animallovers.petfinder.presentation.navigation.SetupNavGraph
import com.animallovers.petfinder.presentation.viewmodel.SplashViewModel
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