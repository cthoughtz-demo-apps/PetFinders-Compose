package com.animallovers.petfinder.domain.usecase.animal

import com.animallovers.petfinder.domain.repository.animal.GetAnimalBreedsRepository
import javax.inject.Inject

class GetAnimalBreedsUseCase @Inject constructor(private val getAnimalBreedsRepository: GetAnimalBreedsRepository) {
    suspend fun getAnimalBreeds(type: String, authToken: String) =
        getAnimalBreedsRepository.getAnimalBreeds(type, authToken)
}