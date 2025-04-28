package com.animallovers.petfinder.presentation.util

import android.os.Build
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext


// TODO Move function to a separate util.kt
@Composable
fun getNavigationMode(): Int? {
    val context = LocalContext.current
    return remember {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            try {
                Settings.Secure.getInt(
                    context.contentResolver,
                    "navigation_mode"
                )
            } catch (e: Exception) {
                null
            }
        } else null
    }
}

// TODO Move function to a separate util.kt
@Composable
fun isThreeButtonNavSystem(): Boolean {
    return getNavigationMode() == 0 // 0=3-button, 1=2-button, 2=gesture
}