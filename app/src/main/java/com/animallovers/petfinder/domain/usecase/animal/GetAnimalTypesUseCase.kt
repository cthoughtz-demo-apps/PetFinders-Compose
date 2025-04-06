package com.animallovers.petfinder.domain.usecase.animal

import com.animallovers.petfinder.domain.repository.animal.GetAnimalTypesRepository
import javax.inject.Inject

class GetAnimalTypesUseCase @Inject constructor(
    private val getAnimalTypesRepository: GetAnimalTypesRepository
) {
    suspend fun getAnimalTypes(authToken: String) =
        getAnimalTypesRepository.getAnimalTypes(authToken)
}