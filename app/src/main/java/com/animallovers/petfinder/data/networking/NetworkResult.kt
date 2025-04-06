package com.animallovers.petfinder.data.networking

import com.animallovers.petfinder.presentation.util.PetFinderResult

sealed class NetworkResult<T> {
    data class Success<T>(val data: T) : NetworkResult<T>()
    data class Failure<T>(val errorMessage: String) : NetworkResult<T>()
}

fun <T> NetworkResult<T>.toResult() = when (this) {
    is NetworkResult.Failure -> PetFinderResult.Failure(this.errorMessage)
    is NetworkResult.Success -> PetFinderResult.Success(this.data)
}