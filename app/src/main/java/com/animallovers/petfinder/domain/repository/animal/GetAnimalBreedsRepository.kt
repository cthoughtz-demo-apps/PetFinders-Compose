package com.animallovers.petfinder.domain.repository.animal

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.animal.GetAnimalBreedsResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetAnimalBreedsRepository {
    suspend fun getAnimalBreeds(type: String,authToken: String) : PetFinderResult<GetAnimalBreedsResponse>
}

class GetAnimalBreedsRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetAnimalBreedsRepository {

    override suspend fun getAnimalBreeds(type: String,authToken: String): PetFinderResult<GetAnimalBreedsResponse> {
        return safeApiCall { apiService.getAnimalBreeds(type, authToken) }.toResult()
    }

}