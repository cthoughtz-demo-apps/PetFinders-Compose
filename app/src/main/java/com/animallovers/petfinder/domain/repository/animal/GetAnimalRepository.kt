package com.animallovers.petfinder.domain.repository.animal

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.animal.GetAnimalResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetAnimalRepository {
    suspend fun getAnimal(id: String, authToken: String) : PetFinderResult<GetAnimalResponse>
}

class GetAnimalRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetAnimalRepository {
    override suspend fun getAnimal(id: String, authToken: String): PetFinderResult<GetAnimalResponse> {
        return safeApiCall { apiService.getAnimal(id, authToken) }.toResult()
    }
}