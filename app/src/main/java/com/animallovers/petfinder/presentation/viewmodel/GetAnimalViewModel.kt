package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.animal.GetAnimalResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalUseCase
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalsUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GetAnimalViewModel @Inject constructor(
    private val getAnimalUseCase: GetAnimalUseCase,
) : ViewModel() {

    private val _getAnimalResultFlow =
        MutableStateFlow<PetFinderResult<GetAnimalResponse>>(PetFinderResult.None)
    val getAnimalResultFlow = _getAnimalResultFlow.asStateFlow()

    fun getAnimal(id: String, accessToken: String) {
        _getAnimalResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = getAnimalUseCase.getAnimal(id, "Bearer $accessToken")
            _getAnimalResultFlow.update { result }
        }
    }
}