package com.animallovers.petfinder.domain.model.animal


import com.google.gson.annotations.SerializedName

data class GetAnimalBreedsResponse(
    @SerializedName("breeds")
    var breeds: List<Breed?>? = null
) {
    data class Breed(
        @SerializedName("_links")
        var links: Links? = null,
        @SerializedName("name")
        var name: String? = null
    ) {
        data class Links(
            @SerializedName("type")
            var type: Type? = null
        ) {
            data class Type(
                @SerializedName("href")
                var href: String? = null
            )
        }
    }
}