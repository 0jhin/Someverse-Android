package com.someverse.presentation.ui.auth.signup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.someverse.presentation.R
import com.someverse.presentation.components.GradientButton
import com.someverse.presentation.components.PageIndicator
import com.someverse.presentation.components.SomeVerseTopBar
import com.someverse.presentation.components.TopBarTitle
import com.someverse.presentation.ui.theme.Black
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import com.someverse.presentation.ui.theme.SomeverseTheme
import com.someverse.presentation.ui.theme.withLetterSpacingPercent

@Composable
fun SignupGenderChoiceScreen(
    onNextClick: () -> Unit = {},
    viewModel: SignupGenderChoiceViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    SignupGenderChoiceScreenContent(
        gender = uiState.gender,
        onGenderSelect = { viewModel.selectGender(it) },
        onButtonClick = onNextClick,
    )
}

@Composable
private fun SignupGenderChoiceScreenContent(
    gender: Gender,
    onGenderSelect: (Gender) -> Unit,
    onButtonClick: () -> Unit,
) {
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

        Text(
            text = "성별을 알려주세요.",
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

        Spacer(modifier = Modifier.height(96.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderItem(
                label = "남성",
                iconRes = R.drawable.ic_gender_man,
                isSelected = gender == Gender.MEN,
                onSelected = { onGenderSelect(Gender.MEN) },
            )
            Spacer(modifier = Modifier.width(Dimensions.space16))
            GenderItem(
                label = "여성",
                iconRes = R.drawable.ic_gender_women,
                isSelected = gender == Gender.WOMEN,
                onSelected = { onGenderSelect(Gender.WOMEN) },
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(5) { index ->
                PageIndicator(isActive = index == 1)
                if (index < 4) Spacer(modifier = Modifier.width(8.dp))
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        GradientButton(
            text = "선택했어요",
            onClick = onButtonClick,
            enabled = gender != Gender.NONE,
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Spacer(modifier = Modifier.height(Dimensions.space16))
    }
}

@Composable
fun GenderItem(
    label: String,
    iconRes: Int,
    isSelected: Boolean,
    onSelected: () -> Unit,
) {
    val borderBrush = if (isSelected) {
        Brush.verticalGradient(
            colors = listOf(
                Color(0xFF7451C9),
                Color(0xFFFD71A6)
            )
        )
    } else {
        Brush.verticalGradient(
            colors = listOf(Color.Transparent, Color.Transparent)
        )
    }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(Dimensions.space8)
    ) {
        Box(
            modifier = Modifier
                .size(92.dp)
                .clip(CircleShape)
                .background(Color(0xFFF8F9FA))
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    brush = borderBrush,
                    shape = CircleShape
                )
                .clickable { onSelected() },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = iconRes),
                contentDescription = label,
                modifier = Modifier.size(52.dp),
                tint = Color.Unspecified
            )
        }

        Spacer(modifier = Modifier.height(Dimensions.space8))

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) Black else Color.Gray,
                fontFamily = PretendardFontFamily
            )
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun SignupGenderChoiceScreenPreView() {
    SomeverseTheme {
        SignupGenderChoiceScreenContent(
            gender = Gender.WOMEN,
            onButtonClick = {
            },
            onGenderSelect = { selectedGender ->
            }
        )
    }
}