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
    ),
    titleTopBar = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 20.sp,
    ),
    subtitleTopBar = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_medium)),
        fontSize = 14.sp,
    ),
    button = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontSize = 14.sp,
    ),
    titleItem = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 14.sp,
    ),
    tag = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_medium)),
        fontSize = 12.sp,
    )

)

data class Typography(
    val brand: TextStyle,
    val iconHint: TextStyle,
    val titleTopBar: TextStyle,
    val subtitleTopBar: TextStyle,
    val button: TextStyle,
    val titleItem: TextStyle,
    val tag: TextStyle,
)