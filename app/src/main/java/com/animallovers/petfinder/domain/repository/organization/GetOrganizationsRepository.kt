package com.animallovers.petfinder.domain.repository.organization

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetOrganizationsRepository {
    suspend fun getOrganizations(authToken: String): PetFinderResult<GetOrganizationsResponse>
}

class GetOrganizationsRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : GetOrganizationsRepository {
    override suspend fun getOrganizations(authToken: String): PetFinderResult<GetOrganizationsResponse> {
        return safeApiCall { apiService.getOrganizations(authToken) }.toResult()
    }

}