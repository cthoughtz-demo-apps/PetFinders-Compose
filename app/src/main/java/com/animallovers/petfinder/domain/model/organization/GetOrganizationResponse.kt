package com.animallovers.petfinder.domain.model.organization


import com.google.gson.annotations.SerializedName

data class GetOrganizationResponse(
    @SerializedName("organization")
    var organization: Organization? = null
) {
    data class Organization(
        @SerializedName("address")
        var address: Address? = null,
        @SerializedName("adoption")
        var adoption: Adoption? = null,
        @SerializedName("distance")
        var distance: Any? = null,
        @SerializedName("email")
        var email: String? = null,
        @SerializedName("hours")
        var hours: Hours? = null,
        @SerializedName("id")
        var id: String? = null,
        @SerializedName("_links")
        var links: Links? = null,
        @SerializedName("mission_statement")
        var missionStatement: Any? = null,
        @SerializedName("name")
        var name: String? = null,
        @SerializedName("phone")
        var phone: String? = null,
        @SerializedName("photos")
        var photos: List<Photo?>? = null,
        @SerializedName("social_media")
        var socialMedia: SocialMedia? = null,
        @SerializedName("url")
        var url: String? = null,
        @SerializedName("website")
        var website: Any? = null
    ) {
        data class Address(
            @SerializedName("address1")
            var address1: String? = null,
            @SerializedName("address2")
            var address2: String? = null,
            @SerializedName("city")
            var city: String? = null,
            @SerializedName("country")
            var country: String? = null,
            @SerializedName("postcode")
            var postcode: String? = null,
            @SerializedName("state")
            var state: String? = null
        )

        data class Adoption(
            @SerializedName("policy")
            var policy: Any? = null,
            @SerializedName("url")
            var url: Any? = null
        )

        data class Hours(
            @SerializedName("friday")
            var friday: Any? = null,
            @SerializedName("monday")
            var monday: Any? = null,
            @SerializedName("saturday")
            var saturday: Any? = null,
            @SerializedName("sunday")
            var sunday: Any? = null,
            @SerializedName("thursday")
            var thursday: Any? = null,
            @SerializedName("tuesday")
            var tuesday: Any? = null,
            @SerializedName("wednesday")
            var wednesday: Any? = null
        )

        data class Links(
            @SerializedName("animals")
            var animals: Animals? = null,
            @SerializedName("self")
            var self: Self? = null
        ) {
            data class Animals(
                @SerializedName("href")
                var href: String? = null
            )

            data class Self(
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

        data class SocialMedia(
            @SerializedName("facebook")
            var facebook: Any? = null,
            @SerializedName("instagram")
            var instagram: Any? = null,
            @SerializedName("pinterest")
            var pinterest: Any? = null,
            @SerializedName("twitter")
            var twitter: Any? = null,
            @SerializedName("youtube")
            var youtube: Any? = null
        )
    }
}