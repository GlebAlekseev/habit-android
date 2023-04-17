package com.glebalekseevjk.habit.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.glebalekseevjk.habit.R

val typography = Typography(
    brand = TextStyle(
        fontFamily = FontFamily(Font(R.font.oregano_regular)),
        fontSize = 84.sp,
        letterSpacing = 2.5.sp,
    ),
    iconHint = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 14.sp,
    )

)

data class Typography(
    val brand: TextStyle,
    val iconHint: TextStyle,
)