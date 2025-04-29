package com.animallovers.petfinder.domain.usecase.organization

import com.animallovers.petfinder.domain.repository.organization.GetOrganizationsRepository
import javax.inject.Inject

class GetOrganizationsUseCase @Inject constructor(private val getOrganizationsRepository: GetOrganizationsRepository) {
    suspend fun getOrganizations(authToken: String) =
        getOrganizationsRepository.getOrganizations(authToken)
}