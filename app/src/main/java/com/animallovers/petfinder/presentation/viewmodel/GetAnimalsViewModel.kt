package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalsUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAnimalsViewModel @Inject constructor(
    val getAnimalsUseCase: GetAnimalsUseCase
) : ViewModel() {

    private val _getAnimalResultFlow =
        MutableStateFlow<PetFinderResult<GetAnimalsResponse>>(PetFinderResult.None)
    val getAnimalsResultFlow = _getAnimalResultFlow.asStateFlow()

    fun getAnimals(accessToken: String) {
        _getAnimalResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = getAnimalsUseCase.getAnimals("Bearer $accessToken")
            _getAnimalResultFlow.update { result }
        }
    }
}