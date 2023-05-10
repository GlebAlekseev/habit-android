package com.glebalekseevjk.habit.ui.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.tooling.preview.Preview
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterialApi::class, ExperimentalMaterial3Api::class)
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
                        text = LocalDate.now().format(DateTimeFormatter.ofPattern("EEEE")).capitalize(),
                        style = typography.titleLarge,
                        modifier = baseModifier.padding(vertical = ExtraSmallPadding)
                    )
                    Text(
                        text = LocalDate.now().format(DateTimeFormatter.ofPattern("d MMMM yyyy г")),
                        modifier = baseModifier.padding(bottom = MiddlePadding),
                        style = typography.bodyMedium
                    )

                    Divider()

                    for ((route, triple) in navMap) {
                        val (action, icon, title) = triple
                        if (route == RouteName.SETTINGS) {
                            Divider()
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
        }
    }
}