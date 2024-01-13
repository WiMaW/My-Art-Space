package com.wioletamwrobel.myartspace.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = Croissantone,
        fontWeight = FontWeight.SemiBold,
        fontSize = 36.sp
    ),
    titleLarge = TextStyle(
        fontFamily = JostSemiBold,
        fontSize = 24.sp
    ),
    titleMedium = TextStyle(
        fontFamily = JostSemiBold,
        fontSize = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = JostLight,
        fontSize = 18.sp
    ),
    bodySmall = TextStyle(
        fontFamily = JostLight,
        fontSize = 15.sp
    ),
    labelSmall = TextStyle(
        fontFamily = JostLight,
        fontSize = 12.sp
    ),
)