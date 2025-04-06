package com.animallovers.petfinder.domain.usecase.animal

import com.animallovers.petfinder.domain.repository.animal.GetAnimalsRepository
import javax.inject.Inject

class GetAnimalsUseCase @Inject constructor(private val getAnimalsRepository: GetAnimalsRepository) {
    suspend fun getAnimals(authToken: String) = getAnimalsRepository.getAnimals(authToken)
}