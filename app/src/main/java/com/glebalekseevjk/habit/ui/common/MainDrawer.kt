package com.glebalekseevjk.habit.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText
import com.glebalekseevjk.habit.ui.widgets.FontSizeRange
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterial3Api::class)
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

    val lazyListState = rememberLazyListState()

    LazyColumn(state = lazyListState) {
        item {

            Row(
                Modifier
                    .fillMaxWidth(0.75f),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.padding(top = LargePadding),
                ) {
                    AutoResizeText(
                        text = stringResource(id = R.string.app_name),
                        style = typography.displayMedium,
                        modifier = baseModifier,
                        color = AppTheme.colors.colorOnPrimary,
                    )
                    AutoResizeText(
                        text = stringResource(id = R.string.app_version),
                        modifier = baseModifier,
                        style = typography.bodyMedium,
                        color = AppTheme.colors.colorOnPrimary
                    )
                    AutoResizeText(
                        text = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern(stringResource(id = R.string.weekday_pattern)))
                            .capitalize(),
                        style = typography.titleLarge,
                        modifier = baseModifier.padding(vertical = ExtraSmallPadding),
                        color = AppTheme.colors.colorOnPrimary
                    )
                    AutoResizeText(
                        text = LocalDate.now()
                            .format(DateTimeFormatter.ofPattern(stringResource(id = R.string.date_pattern))),
                        modifier = baseModifier.padding(bottom = MiddlePadding),
                        style = typography.bodyMedium,
                        color = AppTheme.colors.colorOnPrimary
                    )

                    Divider(color = AppTheme.colors.colorDivider)

                    for ((route, triple) in navMap) {
                        val (action, icon, title) = triple
                        if (route == RouteName.SETTINGS) {
                            Divider(color = AppTheme.colors.colorDivider)
                        }
                        Card(
                            onClick = {
                                action()
                            },
                            colors = CardDefaults.cardColors(
                                contentColor = AppTheme.colors.colorOnPrimary,
                                containerColor = AppTheme.colors.colorPrimary,
                            ),
                        ) {
                            Row(
                                Modifier
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
                                AutoResizeText(
                                    modifier = Modifier.padding(start = MiddlePadding),
                                    text = stringResource(id = title),
                                    style = typography.titleMedium,
                                    color = AppTheme.colors.colorOnPrimary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}