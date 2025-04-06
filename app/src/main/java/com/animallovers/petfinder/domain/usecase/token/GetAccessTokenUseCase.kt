package com.animallovers.petfinder.domain.usecase.token

import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.domain.repository.token.TokenRepository
import javax.inject.Inject

class GetAccessTokenUseCase @Inject constructor(private val getAccessTokenRepository: TokenRepository) {
    suspend fun getAccessToken(tokenRequest: TokenRequest) =
        getAccessTokenRepository.getAccessToken(tokenRequest)
}