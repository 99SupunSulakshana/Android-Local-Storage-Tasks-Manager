package com.example.todoapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.todoapplication.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)

val darkerGrotesque = FontFamily(
    Font(R.font.darkergrotesque_black)
)
val darkerGrotesqueNormal = FontFamily(
    Font(R.font.darkergrotesque_regular)
)
val darkerGrotesqueSemiBold = FontFamily(
    Font(R.font.darkergrotesque_semibold)
)
val darkerGrotesqueBold = FontFamily(
    Font(R.font.darkergrotesque_bold)
)
val firaSansNormal = FontFamily(
    Font(R.font.firasans_regular)
)
val firaSansMedium = FontFamily(
    Font(R.font.firasans_medium)
)
val firaSansSemiBold = FontFamily(
    Font(R.font.firasans_semibold)
)
val firaSansBold = FontFamily(
    Font(R.font.firasans_bold)
)