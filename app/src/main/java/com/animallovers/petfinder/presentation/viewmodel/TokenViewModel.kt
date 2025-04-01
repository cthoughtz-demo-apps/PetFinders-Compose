package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.domain.model.token.TokenResponse
import com.animallovers.petfinder.domain.usecase.animal.GetAnimalsUseCase
import com.animallovers.petfinder.domain.usecase.token.GetAccessTokenUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _tokenResultFlow =
        MutableStateFlow<PetFinderResult<TokenResponse>>(PetFinderResult.None)
        val tokenResultFlow = _tokenResultFlow.asStateFlow()

    fun getAccessToken(tokenRequest: TokenRequest) {
        _tokenResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = tokenUseCase.getAccessToken(tokenRequest)
            _tokenResultFlow.update { result }
        }
    }
}