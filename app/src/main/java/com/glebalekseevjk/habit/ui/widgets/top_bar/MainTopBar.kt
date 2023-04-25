package com.glebalekseevjk.habit.ui.widgets.top_bar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.common.RouteName
import com.glebalekseevjk.habit.ui.theme.*

@Composable
fun MainTopBar(title: String, route: String, onOpenDrawer: () -> Unit, onNavigateToAddHabits: () -> Unit) {
    TopAppBar(
        backgroundColor = AppTheme.colors.colorPrimary,
        elevation = MediumElevation,
        modifier = Modifier.height(64.dp)
    ) {
        Row(
            Modifier
                .align(Alignment.CenterVertically)
                .padding(horizontal = MiddlePadding)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                Modifier.align(Alignment.CenterVertically),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onOpenDrawer,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = MiddlePadding, top = ExtraSmallPadding)
                ) {
                    Icon(
                        Icons.Filled.Menu,
                        stringResource(id = R.string.menu),
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(MiddleIconSize)
                    )
                }

                Text(
                    text = title.toUpperCase(),
                    style = typography.headlineLarge,
                    color = AppTheme.colors.colorOnPrimary,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                )
                if (route == RouteName.TODAY) {
                    Text(
                        text = "18 апр.",
                        style = typography.labelMedium,
                        color = AppTheme.colors.subtitle,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(start = MiddlePadding, top = ExtraSmallPadding)
                    )
                }
            }

            IconButton(
                onClick = onNavigateToAddHabits,
                modifier = Modifier.align(Alignment.CenterVertically).padding(top = ExtraSmallPadding)
            ) {
                Icon(
                    Icons.Filled.Add,
                    stringResource(id = R.string.add_habits),
                    tint = AppTheme.colors.colorOnPrimary,
                    modifier = Modifier.size(MiddleIconSize)
                )
            }
        }
    }
}