package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
import com.animallovers.petfinder.domain.usecase.organization.GetOrganizationsUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrganizationsViewModel @Inject constructor(
    private val getOrganizationsUseCase: GetOrganizationsUseCase
) : ViewModel() {

    private val _getOrganizationsResultFlow =
        MutableStateFlow<PetFinderResult<GetOrganizationsResponse>>(PetFinderResult.None)
    val getOrganizationsResultFlow = _getOrganizationsResultFlow.asStateFlow()

    fun getOrganizations(accessToken: String) {
        _getOrganizationsResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = getOrganizationsUseCase.getOrganizations("Bearer $accessToken")
            _getOrganizationsResultFlow.update { result }
        }
    }
}