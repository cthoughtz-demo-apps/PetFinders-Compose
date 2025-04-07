package com.animallovers.petfinder.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.data.DataStoreRepo
import com.animallovers.petfinder.presentation.navigation.Pages
import kotlinx.coroutines.launch
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepo
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Pages.Splash.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            repository.readOnBoardingState().collect{ completed ->
                if (completed) {
                    _startDestination.value = Pages.Home.route
                } else {
                    _startDestination.value = Pages.Welcome.route
                }
            }
            _isLoading.value = false
        }
    }
}