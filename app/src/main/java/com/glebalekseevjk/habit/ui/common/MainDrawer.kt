package com.glebalekseevjk.habit.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*


@Preview
@Composable
fun MainDrawer(
    onNavigateToToday: () -> Unit = {},
    onNavigateToHabits: () -> Unit = {},
    onNavigateToHistory: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
) {
    val navMap = mapOf(
        RouteName.TODAY to Triple(
            onNavigateToToday,
            ImageVector.vectorResource(id = R.drawable.ic_today),
            R.string.today
        ),
        RouteName.HABITS to Triple(
            onNavigateToHabits,
            ImageVector.vectorResource(id = R.drawable.ic_habits),
            R.string.habits
        ),
        RouteName.HISTORY to Triple(
            onNavigateToHistory,
            ImageVector.vectorResource(id = R.drawable.ic_history),
            R.string.history
        ),
        RouteName.SETTINGS to Triple(
            onNavigateToSettings,
            Icons.Filled.Settings,
            R.string.settings
        ),
    )

    val baseModifier = Modifier.padding(horizontal = LargePadding)

    Row(
        Modifier
            .fillMaxWidth(0.75f),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.padding(top = LargePadding)
            ,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = typography.displayMedium,
                modifier = baseModifier
            )
            Text(
                text = stringResource(id = R.string.app_version),
                modifier = baseModifier,
                style = typography.bodyMedium
            )
            Text(
                text = "Вторник",
                style = typography.titleLarge,
                modifier = baseModifier.padding(vertical = ExtraSmallPadding)
            )
            Text(
                text = "18 апреля 2023 г.",
                modifier = baseModifier.padding(bottom = MiddlePadding),
                style = typography.bodyMedium
            )

            Divider()

            for ((route, triple) in navMap) {
                val (action, icon, title) = triple
                if (route == RouteName.SETTINGS) {
                    Divider()
                }
                Row(
                    Modifier
                        .clickable(
                            indication = rememberRipple(bounded = true, radius = 128.dp, color = AppTheme.colors.colorOnPrimary),
                            interactionSource = remember {
                                MutableInteractionSource()
                            }
                        ){
                            action()
                        }
                        .padding(vertical = SmallPadding, horizontal = LargePadding)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = stringResource(id = title),
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(MiddleIconSize),
                    )
                    Text(
                        modifier = Modifier.padding(start = MiddlePadding),
                        text = stringResource(id = title),
                        style = typography.titleMedium
                    )
                }
            }

        }
    }


}