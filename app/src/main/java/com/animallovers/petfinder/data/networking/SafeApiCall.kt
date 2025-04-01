package com.animallovers.petfinder.data.networking

suspend fun <T>safeApiCall(
    apiCall: suspend() -> T
) : NetworkResult<T> {
    return try {
        apiCall().let { NetworkResult.Success(it) }
    } catch (e: Exception) {
        e.printStackTrace()
        NetworkResult.Failure(e.message ?: "Something Went Wrong")
    }
}