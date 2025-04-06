package com.animallovers.petfinder.presentation.util

import androidx.annotation.DrawableRes
import com.animallovers.petfinder.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {

    object First : OnBoardingPage(
        image = R.drawable.girl_with_dog,
        title = "Find Your Perfect Pet",
        description = "Discover loving pets waiting for a forever home. Start your journey today!"
    )

    object Second : OnBoardingPage(
        image = R.drawable.dog_with_dog,
        title = "Explore Thousands of Pets",
        description = "Browse a variety of pets by breed, age, size, and more to find your ideal match."
    )

    object Third : OnBoardingPage(
        image = R.drawable.hand_with_dog,
        title = "Give a Pet a Loving Home!",
        description = "Swipe, adopt, and bring happiness home. Your new best friend is waiting!"
    )
}