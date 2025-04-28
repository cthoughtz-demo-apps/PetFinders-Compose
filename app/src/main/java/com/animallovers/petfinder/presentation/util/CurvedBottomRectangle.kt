package com.animallovers.petfinder.presentation.util

import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class CurvedBottomRectangle(
    private val curveDepth: Float = 40f // Adjust curve depth
) : Shape {
    override fun createOutline(
        size: androidx.compose.ui.geometry.Size,
        layoutDirection: LayoutDirection,
        density: Density
    ) = Outline.Generic(
        Path().apply {
            moveTo(0f, 0f) // Top-left
            lineTo(size.width, 0f) // Top-right
            lineTo(size.width, size.height - curveDepth) // Bottom-right (above curve)

            // Quadratic Bézier curve for the bottom
            quadraticBezierTo(
                x1 = size.width / 2,
                y1 = size.height + curveDepth,
                x2 = 0f,
                y2 = size.height - curveDepth
            )

            close() // Close the path
        }
    )
}