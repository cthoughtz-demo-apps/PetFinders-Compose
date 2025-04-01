package com.animallovers.petfinder.domain.model.animal


import com.google.gson.annotations.SerializedName

data class GetAnimalResponse(
    @SerializedName("animal")
    var animal: Animal? = null
) {
    data class Animal(
        @SerializedName("age")
        var age: String? = null,
        @SerializedName("attributes")
        var attributes: Attributes? = null,
        @SerializedName("breeds")
        var breeds: Breeds? = null,
        @SerializedName("coat")
        var coat: String? = null,
        @SerializedName("colors")
        var colors: Colors? = null,
        @SerializedName("contact")
        var contact: Contact? = null,
        @SerializedName("description")
        var description: String? = null,
        @SerializedName("distance")
        var distance: Any? = null,
        @SerializedName("environment")
        var environment: Environment? = null,
        @SerializedName("gender")
        var gender: String? = null,
        @SerializedName("id")
        var id: Int? = null,
        @SerializedName("_links")
        var links: Links? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("organization_id")
        var organizationId: String? = null,
        @SerializedName("photos")
        var photos: List<Photo?>? = null,
        @SerializedName("published_at")
        var publishedAt: String? = null,
        @SerializedName("size")
        var size: String? = null,
        @SerializedName("species")
        var species: String? = null,
        @SerializedName("status")
        var status: String? = null,
        @SerializedName("tags")
        var tags: List<String?>? = null,
        @SerializedName("type")
        var type: String? = null,
        @SerializedName("url")
        var url: String? = null,
        @SerializedName("videos")
        var videos: List<Video?>? = null
    ) {
        data class Attributes(
            @SerializedName("declawed")
            var declawed: Boolean? = null,
            @SerializedName("house_trained")
            var houseTrained: Boolean? = null,
            @SerializedName("shots_current")
            var shotsCurrent: Boolean? = null,
            @SerializedName("spayed_neutered")
            var spayedNeutered: Boolean? = null,
            @SerializedName("special_needs")
            var specialNeeds: Boolean? = null
        )

        data class Breeds(
            @SerializedName("mixed")
            var mixed: Boolean? = null,
            @SerializedName("primary")
            var primary: String? = null,
            @SerializedName("secondary")
            var secondary: Any? = null,
            @SerializedName("unknown")
            var unknown: Boolean? = null
        )

        data class Colors(
            @SerializedName("primary")
            var primary: String? = null,
            @SerializedName("secondary")
            var secondary: Any? = null,
            @SerializedName("tertiary")
            var tertiary: Any? = null
        )

        data class Contact(
            @SerializedName("address")
            var address: Address? = null,
            @SerializedName("email")
            var email: String? = null,
            @SerializedName("phone")
            var phone: String? = null
        ) {
            data class Address(
                @SerializedName("address1")
                var address1: Any? = null,
                @SerializedName("address2")
                var address2: Any? = null,
                @SerializedName("city")
                var city: String? = null,
                @SerializedName("country")
                var country: String? = null,
                @SerializedName("postcode")
                var postcode: String? = null,
                @SerializedName("state")
                var state: String? = null
            )
        }

        data class Environment(
            @SerializedName("cats")
            var cats: Boolean? = null,
            @SerializedName("children")
            var children: Boolean? = null,
            @SerializedName("dogs")
            var dogs: Boolean? = null
        )

        data class Links(
            @SerializedName("organization")
            var organization: Organization? = null,
            @SerializedName("self")
            var self: Self? = null,
            @SerializedName("type")
            var type: Type? = null
        ) {
            data class Organization(
                @SerializedName("href")
                var href: String? = null
            )

            data class Self(
                @SerializedName("href")
                var href: String? = null
            )

            data class Type(
                @SerializedName("href")
                var href: String? = null
            )
        }

        data class Photo(
            @SerializedName("full")
            var full: String? = null,
            @SerializedName("large")
            var large: String? = null,
            @SerializedName("medium")
            var medium: String? = null,
            @SerializedName("small")
            var small: String? = null
        )

        data class Video(
            @SerializedName("embed")
            var embed: String? = null
        )
    }
}