package com.glebalekseevjk.habit.ui.widgets.top_bar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.*


@Composable
fun StepTopBar(title: String, onBack: () -> Unit) {
    TopAppBar(
        backgroundColor = AppTheme.colors.colorPrimary,
        elevation = MediumElevation,
        modifier = Modifier.height(ExtraLargePadding)
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
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = MiddlePadding, top = ExtraSmallPadding)
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_back),
                        stringResource(id = R.string.back),
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(MiddleIconSize/2, MiddleIconSize)
                    )
                }

                Text(
                    text = title.toUpperCase(),
                    style = typography.headlineLarge,
                    color = AppTheme.colors.colorOnPrimary,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }
    }
}