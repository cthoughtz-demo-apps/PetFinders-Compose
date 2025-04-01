package com.animallovers.petfinder.domain.repository.token

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.domain.model.token.TokenResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface TokenRepository {
    suspend fun getAccessToken(tokenRequest: TokenRequest): PetFinderResult<TokenResponse>
}

class GetAccessTokenImpl @Inject constructor(
    private val apiService: ApiService
) : TokenRepository {
    override suspend fun getAccessToken(tokenRequest: TokenRequest): PetFinderResult<TokenResponse> {
        return safeApiCall { apiService.getAccessToken(tokenRequest) }.toResult()
    }
}