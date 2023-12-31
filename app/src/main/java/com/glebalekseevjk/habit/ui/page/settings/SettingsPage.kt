package com.glebalekseevjk.habit.ui.page.settings

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Language
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.widgets.AutoResizeText


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsPage(
    onThemeChange: (AppTheme.Theme) -> Unit,
    onLanguageChange: (Language) -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        Modifier
            .fillMaxWidth()
            .padding(top = LargePadding),
    ) {
        Card(
            onClick = {
                expanded = !expanded
            },
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.colorPrimary,
                contentColor = AppTheme.colors.colorOnPrimary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = SmallPadding, horizontal = 64.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(Modifier.weight(1f)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_lang),
                        contentDescription = stringResource(id = R.string.lang),
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(MiddleIconSize),
                    )
                    AutoResizeText(
                        text = stringResource(id = R.string.lang),
                        style = typography.titleMedium,
                        color = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier
                            .padding(horizontal = MiddlePadding)
                            .align(Alignment.CenterVertically)
                    )
                }

                Box(Modifier) {
                    AutoResizeText(
                        text = when (viewStates.language) {
                            Language.RUSSIAN -> "Русский"
                            Language.ENGLISH -> "English"
                        },
                        style = typography.titleSmall,
                        color = AppTheme.colors.colorOnPrimary,
                    )
                    DropdownMenu(
                        expanded = expanded,
                        offset = DpOffset(0.dp, SmallPadding),
                        onDismissRequest = {
                            expanded = !expanded
                        })
                    {
                        DropdownMenuItem(onClick = {
                            onLanguageChange(Language.RUSSIAN)
                            viewModel.dispatch(SettingsViewAction.SetLanguage(Language.RUSSIAN))
                            expanded = !expanded
                        }) {
                            Text(text = "Русский")
                        }
                        DropdownMenuItem(onClick = {
                            onLanguageChange(Language.ENGLISH)
                            viewModel.dispatch(SettingsViewAction.SetLanguage(Language.ENGLISH))
                            expanded = !expanded
                        }) {
                            Text(text = "English")
                        }
                    }
                }

            }
        }

        Card(
            onClick = {
                val theme = if (!viewStates.isDarkTheme) {
                    AppTheme.Theme.Dark
                } else {
                    AppTheme.Theme.Light
                }
                onThemeChange(theme)
                viewModel.dispatch(SettingsViewAction.SetTheme(!viewStates.isDarkTheme))
            },
            colors = CardDefaults.cardColors(
                containerColor = AppTheme.colors.colorPrimary,
                contentColor = AppTheme.colors.colorOnPrimary
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = SmallPadding, horizontal = 64.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(Modifier.weight(1f)) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_night),
                        contentDescription = stringResource(id = R.string.night),
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(MiddleIconSize),
                    )
                    AutoResizeText(
                        text = stringResource(id = R.string.night),
                        style = typography.titleMedium,
                        color = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier
                            .padding(horizontal = MiddlePadding)
                            .align(Alignment.CenterVertically)
                    )
                }

                Switch2(
                    scale = 1.2f,
                    checked = viewStates.isDarkTheme,
                    checkedTrackColor = AppTheme.colors.colorOnPrimary,
                    uncheckedTrackColor = AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f)
                )

            }
        }
    }
}

@Composable
fun Switch2(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckedChange: () -> Unit = {},
    scale: Float = 2f,
    width: Dp = 36.dp,
    height: Dp = 20.dp,
    strokeWidth: Dp = 2.dp,
    checkedTrackColor: Color,
    uncheckedTrackColor: Color,
    gapBetweenThumbAndTrackEdge: Dp = 4.dp
) {
    val thumbRadius = (height / 2) - gapBetweenThumbAndTrackEdge

    val animatePosition = animateFloatAsState(
        targetValue = if (checked)
            with(LocalDensity.current) { (width - thumbRadius - gapBetweenThumbAndTrackEdge).toPx() }
        else
            with(LocalDensity.current) { (thumbRadius + gapBetweenThumbAndTrackEdge).toPx() }
    )

    Canvas(
        modifier = modifier
            .size(width = width, height = height)
            .scale(scale = scale)
    ) {
        drawRoundRect(
            color = if (checked) checkedTrackColor else uncheckedTrackColor,
            cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx()),
            style = Stroke(width = strokeWidth.toPx())
        )
        drawCircle(
            color = if (checked) checkedTrackColor else uncheckedTrackColor,
            radius = thumbRadius.toPx(),
            center = Offset(
                x = animatePosition.value,
                y = size.height / 2
            )
        )
    }
}