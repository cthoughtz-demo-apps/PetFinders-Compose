package com.animallovers.petfinder.data.networking

import com.animallovers.petfinder.domain.model.animal.GetASingleAnimalTypeResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalBreedsResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalTypesResponse
import com.animallovers.petfinder.domain.model.animal.GetAnimalsResponse
import com.animallovers.petfinder.domain.model.organization.GetOrganizationResponse
import com.animallovers.petfinder.domain.model.organization.GetOrganizationsResponse
import com.animallovers.petfinder.domain.model.token.TokenRequest
import com.animallovers.petfinder.domain.model.token.TokenResponse
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // Animal
    @GET("/v2/animals")  // Test to make sure that the Authorization Token works if it does add it to the other function in this interface
    suspend fun getAnimals(@Header("Authorization") authToken: String): GetAnimalsResponse

    @GET("/v2/animals/{id}")
    suspend fun getAnimal(@Path("id") id: String,@Header("Authorization") authToken: String): GetAnimalResponse

    @GET("/v2/types")
    suspend fun getAnimalTypes(@Header("Authorization") authToken: String): GetAnimalTypesResponse

    @GET("/v2/types/{type}")
    suspend fun getASingleAnimal(@Path("type") type: String,@Header("Authorization") authToken: String): GetASingleAnimalTypeResponse

    @GET("/v2/types/{type}/breeds")
    suspend fun getAnimalBreeds(@Path("type") type: String,@Header("Authorization") authToken: String): GetAnimalBreedsResponse

    // Organization
    @GET("/v2/organizations")
    suspend fun getOrganizations(@Header("Authorization") authToken: String): GetOrganizationsResponse

    @GET("/v2/organizations/{id}")
    suspend fun getOrganization(@Path("id") id: String,@Header("Authorization") authToken: String): GetOrganizationResponse

    // Misc
    @POST("/v2/oauth2/token")
    suspend fun getAccessToken(@Body request: TokenRequest): TokenResponse
}