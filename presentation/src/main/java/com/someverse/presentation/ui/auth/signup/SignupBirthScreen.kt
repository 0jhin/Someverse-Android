package com.someverse.presentation.ui.auth.signup

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.someverse.presentation.components.GradientButton
import com.someverse.presentation.components.PageIndicator
import com.someverse.presentation.components.SomeVerseTopBar
import com.someverse.presentation.components.Toast
import com.someverse.presentation.components.TopBarTitle
import com.someverse.presentation.ui.theme.Black
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import com.someverse.presentation.ui.theme.SomeverseTheme
import com.someverse.presentation.ui.theme.withLetterSpacingPercent

@Composable
fun SignupBirthScreen(
    onNextClick: () -> Unit = {},
    viewModel: SignupBirthViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SignupBirthScreenContent(
        year = uiState.year,
        month = uiState.month,
        day = uiState.day,
        age = uiState.age,
        isUnderAge = uiState.isUnderAge,
        showErrorToast = uiState.showErrorToast,
        onDateChange = { y, m, d -> viewModel.updateDate(y, m, d) },
        onButtonClick = onNextClick,
        deleteErrorMessage = viewModel::deleteErrorMessage
    )
}

@Composable
private fun SignupBirthScreenContent(
    year: Int,
    month: Int,
    day: Int,
    age: Int,
    isUnderAge: Boolean,
    showErrorToast: Boolean,
    onDateChange: (Int, Int, Int) -> Unit,
    onButtonClick: () -> Unit,
    deleteErrorMessage: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(Dimensions.screenPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(14.dp))

            SomeVerseTopBar(title = TopBarTitle.Text("회원가입"))

            Spacer(modifier = Modifier.height(Dimensions.space16))

            // 타이틀
            Text(
                text = "생년월일을 알려주세요.",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 21.sp,
                    lineHeight = 31.5.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                ).withLetterSpacingPercent(-2.5f),
                textAlign = TextAlign.Start,
                color = Black,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 날짜 선택기 영역 (Wheel Picker)
            BirthWheelPicker(
                year = year,
                month = month,
                day = day,
                onDateChange = { y, m, d -> onDateChange(y, m, d) }
            )

            Spacer(modifier = Modifier.height(Dimensions.space8))

            Text(
                text = "만 ${age}세",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontFamily = PretendardFontFamily,
                    fontWeight = FontWeight.Medium
                ),
                color = if (isUnderAge) Color.Red else Color.Gray,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 20.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            // 에러 메시지
            if (showErrorToast) {
                Toast(
                    message = "만 14세 이상만 가입할 수 있어요!",
                    onDismiss = { deleteErrorMessage() },
                    duration = 3000L,
                    isError = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // 페이지 인디케이터
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(5) { index ->
                    PageIndicator(isActive = index == 2)
                    if (index < 4) Spacer(modifier = Modifier.width(8.dp))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 다음 버튼
            GradientButton(
                text = "선택했어요",
                onClick = onButtonClick,
                enabled = !isUnderAge,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
            )

            Spacer(modifier = Modifier.height(Dimensions.space16))
        }
    }
}

@Composable
fun BirthWheelPicker(
    year: Int,
    month: Int,
    day: Int,
    onDateChange: (Int, Int, Int) -> Unit
) {
    val years = (1950..2026).toList()
    val months = (1..12).toList()

    val lastDay = when (month) {
        2 -> if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
    val days = (1..lastDay).toList()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(225.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(45.dp)
                .background(Color(0xFFE8E4F9), shape = MaterialTheme.shapes.small)
        )

        Row(modifier = Modifier.fillMaxSize()) {
            // 연도 휠
            WheelColumn(
                items = years,
                initialItem = year,
                modifier = Modifier.weight(1f),
                onItemSelected = { onDateChange(it, month, day) }
            )
            // 월 휠
            WheelColumn(
                items = months,
                initialItem = month,
                modifier = Modifier.weight(1f),
                label = "월",
                onItemSelected = { onDateChange(year, it, day) }
            )
            // 일 휠
            WheelColumn(
                items = days,
                initialItem = day,
                modifier = Modifier.weight(1f),
                onItemSelected = { onDateChange(year, month, it) }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> WheelColumn(
    items: List<T>,
    initialItem: T,
    modifier: Modifier = Modifier,
    label: String = "",
    onItemSelected: (T) -> Unit
) {
    val itemHeight = 45.dp
    val visibleItemCount = 5
    val initialIndex = items.indexOf(initialItem).coerceAtLeast(0)
    val listState = rememberLazyListState(
        initialFirstVisibleItemIndex = initialIndex
    )
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    LaunchedEffect(Unit) {
        listState.scrollToItem(initialIndex)
    }

    LaunchedEffect(listState.isScrollInProgress) {
        if (!listState.isScrollInProgress) {
            val centerIndex = listState.firstVisibleItemIndex
            if (centerIndex in items.indices) {
                onItemSelected(items[centerIndex])
            }
        }
    }

    LazyColumn(
        state = listState,
        flingBehavior = snapFlingBehavior,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .height(itemHeight * visibleItemCount)
            .fillMaxWidth(),
        contentPadding = PaddingValues(vertical = itemHeight * 2)
    ) {
        itemsIndexed(items) { index, item ->
            val isSelected = listState.firstVisibleItemIndex == index

            Box(
                modifier = Modifier
                    .height(itemHeight)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (label.isNotEmpty()) "$item$label" else "$item",
                    style = TextStyle(
                        fontFamily = PretendardFontFamily,
                        fontSize = if (isSelected) 20.sp else 17.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color(0xFF7451C9) else Color(0xFFADB5BD),
                        platformStyle = PlatformTextStyle(includeFontPadding = false)
                    ),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "정상 케이스 (만 14세 이상)")
fun SignupBirthScreenSuccessPreview() {
    SomeverseTheme {
        SignupBirthScreenContent(
            year = 1991,
            month = 4,
            day = 4,
            age = 39,
            isUnderAge = false,
            showErrorToast = false,
            onDateChange = { _, _, _ -> },
            onButtonClick = {},
            deleteErrorMessage = {}
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "에러 케이스 (만 14세 미만)")
fun SignupBirthScreenErrorPreview() {
    SomeverseTheme {
        SignupBirthScreenContent(
            year = 2015,
            month = 5,
            day = 20,
            age = 39,
            isUnderAge = true,
            showErrorToast = true,
            onDateChange = { _, _, _ -> },
            onButtonClick = {},
            deleteErrorMessage = {}
        )
    }
}