package com.animallovers.petfinder.presentation.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.domain.model.token.TokenResponse
import com.animallovers.petfinder.domain.usecase.token.GetAccessTokenUseCase
import com.animallovers.petfinder.presentation.util.Constants.CLIENT_ID
import com.animallovers.petfinder.presentation.util.Constants.CLIENT_SECRET
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenViewModel @Inject constructor(
    private val tokenUseCase: GetAccessTokenUseCase
) : ViewModel() {

    private val _tokenResultFlow =
        MutableStateFlow<PetFinderResult<TokenResponse>>(PetFinderResult.None)

    var authToken by mutableStateOf<String?>(null)


    init {
        getAccessToken(
            TokenRequest(
                clientId = CLIENT_ID,
                clientSecret = CLIENT_SECRET
            )
        )
    }

    private fun getAccessToken(tokenRequest: TokenRequest) {
        _tokenResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = tokenUseCase.getAccessToken(tokenRequest)
            setToken(result)
            _tokenResultFlow.update { result }
        }
    }

    private fun setToken(result: PetFinderResult<TokenResponse>) {
        when (result) {
            is PetFinderResult.Loading -> {
                Log.d("TokenViewModel", "setToken: Loading")
            }

            is PetFinderResult.Success -> {
                authToken = result.data.accessToken
            }

            is PetFinderResult.Failure -> {
                Log.d("TokenViewModel", "setToken: Something Went Wrong")
            }

            else -> {}
        }
    }
}