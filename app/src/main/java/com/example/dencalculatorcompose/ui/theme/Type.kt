package com.example.dencalculatorcompose.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.dencalculatorcompose.R

val googleSans = FontFamily(
    Font(resId = R.font.google_sans_regular)
)

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = googleSans,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp
    ),
    h1 = TextStyle(
        fontFamily = googleSans,
        fontWeight = FontWeight.Medium,
        fontSize = 57.sp
    ),
    h2 = TextStyle(
        fontFamily = googleSans,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        color = whiteGray
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)