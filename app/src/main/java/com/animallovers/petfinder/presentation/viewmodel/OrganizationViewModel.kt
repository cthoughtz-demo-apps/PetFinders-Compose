package com.animallovers.petfinder.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.animallovers.petfinder.domain.model.organization.GetOrganizationResponse
import com.animallovers.petfinder.domain.usecase.organization.GetOrganizationUseCase
import com.animallovers.petfinder.presentation.util.PetFinderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OrganizationViewModel @Inject constructor(
    private val getOrganizationUseCase: GetOrganizationUseCase
) : ViewModel() {

   private val _organizationResultFlow =
    MutableStateFlow<PetFinderResult<GetOrganizationResponse>>(PetFinderResult.None)
   val organizationResultFlow = _organizationResultFlow.asStateFlow()

    fun getOrganization(id: String, accessToken: String) {
        _organizationResultFlow.update { PetFinderResult.Loading }
        viewModelScope.launch {
            val result = getOrganizationUseCase.getOrganization(id, "Bearer $accessToken")
            _organizationResultFlow.update { result }
        }
    }
}