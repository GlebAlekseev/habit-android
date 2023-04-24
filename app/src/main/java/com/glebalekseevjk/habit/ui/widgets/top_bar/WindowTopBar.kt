package com.glebalekseevjk.habit.ui.widgets.top_bar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.ui.theme.AppTheme


@Composable
fun WindowTopBar(onBack: () -> Unit) {
    TopAppBar(
        backgroundColor = AppTheme.colors.colorPrimary,
        elevation = 5.dp
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .align(Alignment.CenterVertically)
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(Modifier.align(Alignment.CenterVertically)) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(end = 10.dp)
                ) {
                    Icon(
                        ImageVector.vectorResource(id = R.drawable.ic_back),
                        "Закрыть",
                        tint = AppTheme.colors.colorOnPrimary,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        }
    }
}