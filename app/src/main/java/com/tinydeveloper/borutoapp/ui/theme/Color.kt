package com.tinydeveloper.borutoapp.ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.ContentAlpha
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val dark_primary_dark = Color(0xFF2C2C2D)
val dark_primary = Color(0xFF707070)
val dark_on_primary = Color(0xFF5C4D34)
val dark_text = Color(0xFFFFFFFF)

val primary_dark = Color(0xFF707070)
val primary = Color(0xFFDFDFDF)
val on_primary = Color(0xFF5C4D34)
val second_on_primary = Color(0xFF97815C)
val text = Color(0xFF000000)

val Star = Color(0xFFFFC94D)
val EmptyStar = Color.LightGray

val Black = Color(0xFF000000)
val White = Color(0xFFFFFFFF)

val ShimmerLightGray = Color(0xFFF1F1F1)
val ShimmerMediumGray = Color(0xFFCFCECE)
val ShimmerDarkGray = Color(0xFF1D1D1D)

val HalfWhite @Composable get() = Color.White.copy(alpha =  ContentAlpha.medium)

val HalfBlack @Composable get() =  Color.Black.copy(alpha = ContentAlpha.medium)

val Colors.welcomeScreenBackground get() = if (isLight) primary else dark_primary_dark

val Colors.titleColor get() = if (isLight) Color.DarkGray else Color.LightGray

val Colors.descriptionColor
    get() = if (isLight) Color.DarkGray.copy(alpha = 0.5f) else Color.LightGray.copy(alpha = 0.5f)

val Colors.activeIndicator get() = if (isLight) on_primary else Color.LightGray

val Colors.inActiveIndicator get() = if (isLight) dark_primary else dark_primary

val Colors.buttonBackgroundColor get() = if (isLight) on_primary else second_on_primary

val Colors.topAppBarContentColor get() = if (isLight) Color.White else Color.LightGray

val Colors.topAppBarBackgroundColor get() = if (isLight) on_primary else dark_primary_dark

val Colors.contentColor get() = if (isLight) Color.White else Color.LightGray

val Colors.caverColor @Composable get() = if (isLight) HalfBlack else dark_primary_dark.copy(alpha = 0.7f)
