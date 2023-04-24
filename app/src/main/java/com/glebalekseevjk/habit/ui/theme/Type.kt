package com.glebalekseevjk.habit.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.glebalekseevjk.habit.R

val typography = Typography(
    // Крупные заголовки
    displayLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.oregano_regular)),
        fontSize = 84.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.oregano_regular)),
        fontSize = 56.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.oregano_regular)),
        fontSize = 28.sp,
    ),

    // Заголовки текстовых блоков
    headlineLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_medium)),
        fontSize = 18.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_medium)),
        fontSize = 12.sp,
    ),



    // Элементы пользовательского интерфейса (кнопки..)
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontSize = 24.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontSize = 18.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_bold)),
        fontSize = 12.sp,
    ),


    // Основной текст контента
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 24.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 18.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.roboto_regular)),
        fontSize = 12.sp,
    ),


    // Поля ввода
    labelLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 14.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.raleway_bold)),
        fontSize = 14.sp,
    ),


)

data class Typography(
    val displayLarge: TextStyle,
    val displayMedium: TextStyle,
    val displaySmall: TextStyle,
    val headlineLarge: TextStyle,
    val headlineMedium: TextStyle,
    val headlineSmall: TextStyle,
    val titleLarge: TextStyle,
    val titleMedium: TextStyle,
    val titleSmall: TextStyle,
    val bodyLarge: TextStyle,
    val bodyMedium: TextStyle,
    val bodySmall: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
)