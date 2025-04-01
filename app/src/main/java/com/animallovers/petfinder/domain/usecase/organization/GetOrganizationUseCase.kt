package com.animallovers.petfinder.domain.usecase.organization

import com.animallovers.petfinder.domain.repository.organization.GetOrganizationRepository
import javax.inject.Inject

class GetOrganizationUseCase @Inject constructor(private val getOrganizationRepository: GetOrganizationRepository) {
    suspend fun getOrganization(id: String, authToken: String) = getOrganizationRepository.getOrganization(id, authToken)
}