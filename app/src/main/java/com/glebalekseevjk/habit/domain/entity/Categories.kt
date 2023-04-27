package com.glebalekseevjk.habit.domain.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.glebalekseevjk.habit.R

data class Categories(
    val id: Int,
    val titleStringId: Int,
    val descriptionStringId: Int,
    val iconId: Int,
    val colorRGBA: Int,
) {
    companion object {
        val categoriesList = listOf(
            Categories(
                id = 1,
                titleStringId = R.string.categories_trend_title,
                descriptionStringId = R.string.categories_trend_description,
                iconId = R.drawable.ic_categories_trend,
                colorRGBA = Color(0xFF4A7CF7).toArgb(),
            ),
            Categories(
                id = 2,
                titleStringId = R.string.categories_important_title,
                descriptionStringId = R.string.categories_important_description,
                iconId = R.drawable.ic_categories_important,
                colorRGBA = Color(0xFFF8BA2D).toArgb(),
            ),
            Categories(
                id = 3,
                titleStringId = R.string.categories_eat_title,
                descriptionStringId = R.string.categories_eat_description,
                iconId = R.drawable.ic_categories_eat,
                colorRGBA = Color(0xFFb3d5e9).toArgb(),
            ),
            Categories(
                id = 4,
                titleStringId = R.string.categories_sports_title,
                descriptionStringId = R.string.categories_sports_description,
                iconId = R.drawable.ic_categories_sports,
                colorRGBA = Color(0xFF14b1ab).toArgb(),
            ),
            Categories(
                id = 5,
                titleStringId = R.string.categories_morning_title,
                descriptionStringId = R.string.categories_morning_description,
                iconId = R.drawable.ic_categories_morning,
                colorRGBA = Color(0xFFffa372).toArgb(),
            ),
            Categories(
                id = 6,
                titleStringId = R.string.categories_sleep_title,
                descriptionStringId = R.string.categories_sleep_description,
                iconId = R.drawable.ic_categories_sleep,
                colorRGBA = Color(0xFF2a3d66).toArgb(),
            ),
            Categories(
                id = 7,
                titleStringId = R.string.categories_productivity_title,
                descriptionStringId = R.string.categories_productivity_description,
                iconId = R.drawable.ic_categories_productivity,
                colorRGBA = Color(0xFF00adb5).toArgb(),
            ),
            Categories(
                id = 8,
                titleStringId = R.string.categories_leisure_title,
                descriptionStringId = R.string.categories_leisure_description,
                iconId = R.drawable.ic_categories_leisure,
                colorRGBA = Color(0xFF8675a9).toArgb(),
            ),
            Categories(
                id = 9,
                titleStringId = R.string.categories_budget_title,
                descriptionStringId = R.string.categories_budget_description,
                iconId = R.drawable.ic_categories_budget,
                colorRGBA = Color(0xFFF8BA2D).toArgb(),
            ),
            Categories(
                id = 10,
                titleStringId = R.string.categories_pets_title,
                descriptionStringId = R.string.categories_pets_description,
                iconId = R.drawable.ic_categories_pets,
                colorRGBA = Color(0xFFffa36c).toArgb(),
            ),
        )
    }
}