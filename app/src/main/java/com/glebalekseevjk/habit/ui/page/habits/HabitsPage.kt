package com.glebalekseevjk.habit.ui.page.habits

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.glebalekseevjk.habit.R
import com.glebalekseevjk.habit.domain.entity.Habit.Companion.RepeatType.SPECIFIC_DAYS
import com.glebalekseevjk.habit.ui.theme.*
import com.glebalekseevjk.habit.ui.utils.pxToDp


@Composable
fun HabitsPage(
    onNavigateToAddHabits: () -> Unit,
    onNavigateToEditHabits: (Int) -> Unit,
    viewModel: HabitsViewModel = hiltViewModel()
) {
    val viewStates = viewModel.viewStates

    LaunchedEffect(Unit) {
        println(">>>>>>>>>>>>>> viewmodel=$viewModel")
        if (viewStates is HabitsViewState.Init) {
            viewModel.dispatch(HabitsViewAction.InitHabits)
        }
    }

    when (viewStates) {
        HabitsViewState.Failure -> {
            TODO("Ошибка")
        }
        is HabitsViewState.Loading, is HabitsViewState.Init -> {
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
        is HabitsViewState.Loaded -> {
            LazyColumn(
                state = viewStates.lazyListState,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewStates.habitList) {
                    var elementHeight by remember {
                        mutableStateOf(0)
                    }
                    Column(
                        Modifier
                            .padding(MiddlePadding)
                            .background(
                                AppTheme.colors.colorOnPrimary.copy(alpha = 0.1f),
                                shape = RoundedCornerShape(SmallPadding)
                            )
                            .padding(SmallPadding)
                            .fillMaxWidth()
                    ) {
                        Row(
                            Modifier
                                .padding(SmallPadding)
                                .fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(Modifier.height(elementHeight.pxToDp())) {
                                Box(
                                    modifier = Modifier
                                        .padding(end = MiddlePadding)
                                        .background(Color.Green)
                                        .width(ExtraSmallPadding)
                                        .fillMaxHeight()
                                )
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxHeight()
                                ) {
                                    Text(
                                        text = it.habit.title,
                                        style = typography.headlineMedium,
                                        color = AppTheme.colors.colorOnPrimary
                                    )
                                    when (it.habit.repeatType) {
                                        SPECIFIC_DAYS -> {
                                            Box(
                                                contentAlignment = Alignment.Center,
                                                modifier = Modifier
                                                    .background(
                                                        Color.Green.copy(alpha = 0.2f),
                                                        shape = RoundedCornerShape(ExtraSmallPadding),
                                                    )
                                                    .padding(ExtraSmallPadding)
                                            ) {
                                                Text(
                                                    text = "Каждый день",
                                                    color = Color.Green,
                                                    style = typography.titleSmall
                                                )
                                            }
                                        }
                                    }
                                }
                            }

                            Box(
                                Modifier
                                    .background(
                                        Color(it.habit.colorRGBA).copy(alpha = 0.25f),
                                        shape = RoundedCornerShape(SmallPadding)
                                    )
                                    .onSizeChanged {
                                        if (elementHeight != it.height) {
                                            elementHeight = it.height
                                        }
                                    }
                                    .padding(MiddlePadding),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = it.habit.iconId),
                                    contentDescription = it.habit.title,
                                    tint = Color(it.habit.colorRGBA),
                                    modifier = Modifier.size(SmallIconSize)
                                )
                            }
                        }

//                        Row(
//                            horizontalArrangement = Arrangement.Center,
//                            modifier = Modifier.fillMaxWidth()
//                        ) {
//                            TODO("Парсить и выводить")
////                            it.dayList.forEach {
////                                Column(
////                                    verticalArrangement = Arrangement.Center,
////                                    horizontalAlignment = Alignment.CenterHorizontally,
////                                    modifier = Modifier.padding(horizontal = 3.dp)
////                                ) {
////                                    Text(
////                                        text = it.day,
////                                        color = AppTheme.colors.colorOnPrimary,
////                                        modifier = Modifier.padding(bottom = SmallPadding)
////                                    )
////                                    Box(
////                                        modifier = Modifier
////                                            .background(
////                                                AppTheme.colors.colorOnPrimary.copy(alpha = 0.2f),
////                                                shape = RoundedCornerShape(100.dp)
////                                            )
////                                            .padding(SmallPadding),
////                                        contentAlignment = Alignment.Center
////                                    ) {
////                                        Text(
////                                            text = "${it.dayInt}",
////                                            color = AppTheme.colors.colorOnPrimary
////                                        )
////                                    }
////                                }
////                            }
//                        }

                        Divider(
                            modifier = Modifier.padding(vertical = SmallPadding),
                            thickness = 2.dp,
                            color = AppTheme.colors.divider
                        )
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = SmallPadding)
                        ) {
                            Row() {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_progress_check),
                                    contentDescription = null,
                                    tint = Color.Green
                                )
                                Text(
//                                    text = "${it.stage}%",
                                    text = "11%",
                                    color = Color.Green,
                                    modifier = Modifier.padding(start = 12.dp)
                                )
                            }
                            Row() {
                                Icon(
                                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_edit),
                                    contentDescription = "Редактировать",
                                    modifier = Modifier
                                        .clickable(
                                            indication = rememberRipple(
                                                bounded = true,
                                                color = AppTheme.colors.colorOnPrimary
                                            ),
                                            interactionSource = remember {
                                                MutableInteractionSource()
                                            }
                                        ) {
                                            onNavigateToEditHabits(it.habit.id)
                                        }
                                        .padding(horizontal = 12.dp),
                                    tint = AppTheme.colors.colorOnPrimary
                                )
                            }
                        }

                    }
                }

                item() {
                    Row(Modifier.padding(vertical = MiddlePadding)) {
                        Button(
                            onClick = onNavigateToAddHabits,
                            modifier = Modifier,
                            contentPadding = PaddingValues(
                                vertical = MiddlePadding,
                                horizontal = LargePadding
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colors.colorOnPrimary,
                                contentColor = AppTheme.colors.colorPrimary
                            )
                        ) {
                            Text(
                                text = "Создайте новую привычку".toUpperCase(),
                                style = typography.titleMedium,
                                color = AppTheme.colors.colorPrimary
                            )
                        }
                    }
                }
            }
        }
    }
}