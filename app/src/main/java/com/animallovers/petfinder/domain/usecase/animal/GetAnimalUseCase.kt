package com.animallovers.petfinder.domain.usecase.animal

import com.animallovers.petfinder.domain.repository.animal.GetAnimalRepository
import javax.inject.Inject

class GetAnimalUseCase @Inject constructor(private val getAnimalRepository: GetAnimalRepository) {
    suspend fun getAnimal(id: String, authToken: String) =
        getAnimalRepository.getAnimal(id, authToken)
}