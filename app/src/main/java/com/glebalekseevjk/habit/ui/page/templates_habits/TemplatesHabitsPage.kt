package com.glebalekseevjk.habit.ui.page.templates_habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*

@Composable
fun TemplatesHabitsPage(templatesId: Int, onNavigateToEditHabits: (Int) -> Unit) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(LargePadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Доброе утро",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.titleLarge,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            text = "Делайте то, что правильно по утрам",
            color = AppTheme.colors.colorOnPrimary,
            style = typography.bodySmall,
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            Modifier
                .padding(top = SmallPadding)
                .clickable(
                    indication = rememberRipple(
                        bounded = true,
                        color = AppTheme.colors.colorOnPrimary
                    ),
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) {
                    onNavigateToEditHabits(21)
                }
                .background(
                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
                    shape = smallRoundedCornerShape
                )
                .padding(
                    start = LargePadding,
                    end = MiddlePadding,
                    top = MiddlePadding,
                    bottom = MiddlePadding
                )
                .fillMaxWidth(1f),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sleep),
                contentDescription = "Вставайте рано",
                modifier = Modifier
                    .size(LargeIconSize),
                tint = Color(0xFFFFC46C)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(start = MiddlePadding)
            ) {
                Text(
                    text = "Вставайте рано",
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.titleSmall
                )
                Text(
                    text = "Светите раньше солнца",
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.bodySmall,
                    modifier = Modifier
                )
            }
        }
    }
}