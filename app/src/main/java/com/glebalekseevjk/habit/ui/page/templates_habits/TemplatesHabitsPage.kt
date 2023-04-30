package com.glebalekseevjk.habit.ui.page.templates_habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.ui.theme.*

@Composable
fun TemplatesHabitsPage(
    categoryId: Int,
    onNavigateToEditHabits: (Int) -> Unit,
    viewModel: TemplatesViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LaunchedEffect(Unit) {
        viewModel.dispatch(TemplatesViewAction.InitTemplates(categoryId))
    }

    when (viewStates) {
        TemplatesViewState.Failure -> TODO("Ошибка")
        is TemplatesViewState.Init, is TemplatesViewState.Loading -> {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.15f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colors.colorOnPrimary,
                    strokeWidth = 2.dp
                )
            }
        }
        is TemplatesViewState.Loaded -> {

            LazyColumn(
                state = viewStates.lazyListState,
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    val titleCategory = stringResource(id = viewStates.category.titleStringId)
                    val descriptionCategory =
                        stringResource(id = viewStates.category.descriptionStringId)
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(LargePadding),
                    ) {
                        Text(
                            text = titleCategory,
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.titleLarge,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = descriptionCategory,
                            color = AppTheme.colors.colorOnPrimary,
                            style = typography.bodySmall,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                items(viewStates.templateList) {
                    Row(
                        Modifier
                            .padding(top = SmallPadding)
                            .padding(horizontal = LargePadding)
                            .clickable(
                                indication = rememberRipple(
                                    bounded = true,
                                    color = AppTheme.colors.colorOnPrimary
                                ),
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                onNavigateToEditHabits(it.id)
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
                            imageVector = ImageVector.vectorResource(id = it.iconId),
                            contentDescription = stringResource(id = it.titleStringId),
                            modifier = Modifier
                                .size(LargeIconSize),
                            tint = Color(it.colorRGBA)
                        )
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(start = MiddlePadding)
                        ) {
                            Text(
                                text = stringResource(id = it.titleStringId),
                                color = AppTheme.colors.colorOnPrimary,
                                style = typography.titleSmall
                            )
                            Text(
                                text = stringResource(id = it.descriptionStringId),
                                color = AppTheme.colors.colorOnPrimary,
                                style = typography.bodySmall,
                                modifier = Modifier
                            )
                        }
                    }
                }

                item {
                    Row(modifier = Modifier.padding(vertical = SmallPadding)) {}
                }
            }
        }
    }
}