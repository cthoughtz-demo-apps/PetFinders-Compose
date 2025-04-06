package com.animallovers.petfinder.presentation.util

sealed class PetFinderResult<out T> {
    data object Loading : PetFinderResult<Nothing>()
    data class Success<T>(val data: T) : PetFinderResult<T>()
    data class Failure<T>(val errorMessage: String) : PetFinderResult<T>()
    data object None : PetFinderResult<Nothing>()
}