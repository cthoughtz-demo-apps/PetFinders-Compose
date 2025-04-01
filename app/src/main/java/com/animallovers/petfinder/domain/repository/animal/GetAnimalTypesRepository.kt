package com.animallovers.petfinder.domain.repository.animal

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.animal.GetAnimalTypesResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetAnimalTypesRepository {
    suspend fun getAnimalTypes(authToken: String) : PetFinderResult<GetAnimalTypesResponse>
}

class GetAnimalTypesRepositoryImpl @Inject constructor(
    private val apiService: ApiService
): GetAnimalTypesRepository {
    override suspend fun getAnimalTypes(authToken: String): PetFinderResult<GetAnimalTypesResponse> {
        return safeApiCall { apiService.getAnimalTypes(authToken) }.toResult()
    }

}