package com.animallovers.petfinder.domain.usecase.animal

import com.animallovers.petfinder.domain.repository.animal.GetASingleAnimalTypeRepository
import javax.inject.Inject

class GetASingleAnimalUseCase @Inject constructor(private val getASingleAnimalTypeRepository: GetASingleAnimalTypeRepository) {
    suspend fun getASingleAnimal(type: String, authToken: String) = getASingleAnimalTypeRepository.getASingleAnimal(type, authToken)
}