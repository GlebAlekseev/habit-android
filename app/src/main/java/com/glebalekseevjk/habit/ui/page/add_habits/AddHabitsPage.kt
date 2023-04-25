package com.glebalekseevjk.habit.ui.page.add_habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*

private const val REGULAR = 1
private const val HARMFUL = 2
private const val DISPOSABLE = 3


@Composable
fun AddHabitsPage() {
    var habitsType by rememberSaveable {
        mutableStateOf(REGULAR)
    }

    val habitsTypeMap = mapOf(
        REGULAR to Triple(
            stringResource(id = R.string.regular),
            ImageVector.vectorResource(id = R.drawable.ic_regular),
            "Часть ваших повседневных привычек. Регулярно помечайте их как выполненные."
        ),
        HARMFUL to Triple(
            stringResource(id = R.string.harmful),
            ImageVector.vectorResource(id = R.drawable.ic_harmful),
            "Вредная .."
        ),
        DISPOSABLE to Triple(
            stringResource(id = R.string.disposable),
            ImageVector.vectorResource(id = R.drawable.ic_disposable),
            "Одноразовая .."
        )
    )

    Column(
        Modifier
            .fillMaxWidth()
            .padding(MiddlePadding + SmallPadding),
    horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            habitsTypeMap.forEach {
                val (title, icon) = it.value
                val (contentColor, backgroundColor) = if (habitsType == it.key)
                    Pair(
                        AppTheme.colors.colorPrimary,
                        AppTheme.colors.colorOnPrimary
                    )
                else Pair(
                    AppTheme.colors.colorOnPrimary,
                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f)
                )

                Column(
                    Modifier
                        .clickable(
                            indication = rememberRipple(bounded = true, radius = 128.dp, color = AppTheme.colors.colorOnPrimary),
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ){
                            habitsType = it.key
                        }
                        .background(
                            backgroundColor,
                            shape = smallRoundedCornerShape
                        )
                        .padding(MiddlePadding),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        modifier = Modifier
                            .padding(vertical = MiddlePadding)
                            .size(LargeIconSize),
                        tint = contentColor
                    )
                    Text(
                        text = title.toUpperCase(),
                        color = contentColor,
                        style = typography.titleSmall
                    )
                }
            }
        }

        val (title, _, text) = habitsTypeMap[habitsType]!!

        Column(
            Modifier
                .padding(top = SmallPadding)
                .background(
                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
                    shape = smallRoundedCornerShape
                )
                .padding(MiddlePadding)
                .fillMaxWidth()
        ) {
            Text(
                text = title.toUpperCase(),
                color = AppTheme.colors.colorOnPrimary,
                style = typography.titleSmall
            )
            Text(
                text = text,
                color = AppTheme.colors.colorOnPrimary,
                style = typography.bodySmall,
                modifier = Modifier.padding(top = SmallPadding)
            )
        }

        Button(
            onClick = {},
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = SmallPadding),
            shape = mediumRoundedCornerShape,
            contentPadding = PaddingValues(
                vertical = SmallPadding,
                horizontal = LargePadding
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.colorOnPrimary,
                contentColor = AppTheme.colors.colorPrimary
            )
        ) {
            Text(
                text = "Создать привычку".toUpperCase(),
                style = typography.titleSmall,
                color = AppTheme.colors.colorPrimary
            )
        }

        Text(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = SmallPadding),
            text = stringResource(id = R.string.templates_label).toUpperCase(),
            style = typography.titleSmall,
            color = AppTheme.colors.colorOnPrimary
        )


        Row(
            Modifier
                .padding(top = SmallPadding)
                .background(
                    AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
                    shape = smallRoundedCornerShape
                )
                .padding(MiddlePadding)
                .fillMaxWidth(0.9f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_sun),
                contentDescription = "Доброе утро",
                modifier = Modifier
                    .size(LargeIconSize),
                tint = Color(0xFFFFC46C)
            )
            Column(verticalArrangement = Arrangement.Center, modifier = Modifier.padding(start = MiddlePadding)) {
                Text(
                    text = "Доброе утро",
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.titleSmall
                )
                Text(
                    text = "Делайте то, что правильно, по утрам",
                    color = AppTheme.colors.colorOnPrimary,
                    style = typography.bodySmall,
                    modifier = Modifier
                )
            }
        }

    }


}

