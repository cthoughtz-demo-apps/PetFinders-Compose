package com.animallovers.petfinder.domain.model.token

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("grant_type") val grantType: String = "client_credentials",
    @SerializedName("client_id") val clientId: String,
    @SerializedName("client_secret") val clientSecret: String
)
