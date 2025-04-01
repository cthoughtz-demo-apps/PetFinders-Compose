package com.animallovers.petfinder.domain.model.animal


import com.google.gson.annotations.SerializedName

data class GetAnimalTypesResponse(
    @SerializedName("types")
    var types: List<Type?>? = null
) {
    data class Type(
        @SerializedName("coats")
        var coats: List<String?>? = null,
        @SerializedName("colors")
        var colors: List<String?>? = null,
        @SerializedName("genders")
        var genders: List<String?>? = null,
        @SerializedName("_links")
        var links: Links? = null,
        @SerializedName("name")
        var name: String? = null
    ) {
        data class Links(
            @SerializedName("breeds")
            var breeds: Breeds? = null,
            @SerializedName("self")
            var self: Self? = null
        ) {
            data class Breeds(
                @SerializedName("href")
                var href: String? = null
            )

            data class Self(
                @SerializedName("href")
                var href: String? = null
            )
        }
    }
}