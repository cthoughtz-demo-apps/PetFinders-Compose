package com.animallovers.petfinder.domain.repository.animal

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.animal.GetASingleAnimalTypeResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetASingleAnimalTypeRepository {
    suspend fun getASingleAnimal(
        type: String,
        authToken: String
    ): PetFinderResult<GetASingleAnimalTypeResponse>
}

class GetASingleAnimalTypeRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetASingleAnimalTypeRepository {
    override suspend fun getASingleAnimal(
        type: String,
        authToken: String
    ): PetFinderResult<GetASingleAnimalTypeResponse> {
        return safeApiCall { apiService.getASingleAnimal(type, authToken) }.toResult()
    }

}