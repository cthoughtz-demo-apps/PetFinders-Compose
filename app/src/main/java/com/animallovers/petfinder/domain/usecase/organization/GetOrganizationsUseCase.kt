package com.animallovers.petfinder.domain.usecase.organization

import com.animallovers.petfinder.domain.repository.organization.GetOrganizationRepository
import javax.inject.Inject

class GetOrganizationsUseCase @Inject constructor(private val getOrganizationsRepository: GetOrganizationRepository) {
    suspend fun getOrganizations(id: String, authToken: String) = getOrganizationsRepository.getOrganization(id, authToken)
}