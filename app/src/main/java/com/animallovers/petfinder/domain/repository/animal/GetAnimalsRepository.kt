package com.animallovers.petfinder.domain.repository.animal

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetAnimalsRepository {
    suspend fun getAnimals(authToken: String): PetFinderResult<GetAnimalsResponse>
}

class GetAnimalsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetAnimalsRepository {
    override suspend fun getAnimals(authToken: String): PetFinderResult<GetAnimalsResponse> {
        return safeApiCall { apiService.getAnimals(authToken) }.toResult()
    }
}