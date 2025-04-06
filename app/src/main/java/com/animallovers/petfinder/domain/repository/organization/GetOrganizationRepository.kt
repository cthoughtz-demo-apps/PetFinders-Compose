package com.animallovers.petfinder.domain.repository.organization

import com.animallovers.petfinder.data.networking.ApiService
import com.animallovers.petfinder.data.networking.safeApiCall
import com.animallovers.petfinder.data.networking.toResult
import com.animallovers.petfinder.domain.model.organization.GetOrganizationResponse
import com.animallovers.petfinder.presentation.util.PetFinderResult
import javax.inject.Inject

interface GetOrganizationRepository {
    suspend fun getOrganization(
        id: String,
        authToken: String
    ): PetFinderResult<GetOrganizationResponse>
}

class GetOrganizationRepositoryImpl @Inject constructor(private val apiService: ApiService) :
    GetOrganizationRepository {
    override suspend fun getOrganization(
        id: String,
        authToken: String
    ): PetFinderResult<GetOrganizationResponse> {
        return safeApiCall { apiService.getOrganization(id, authToken) }.toResult()
    }
}