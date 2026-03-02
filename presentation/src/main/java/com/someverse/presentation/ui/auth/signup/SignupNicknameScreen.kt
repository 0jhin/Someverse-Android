package com.someverse.presentation.ui.auth.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.PlaceholderVerticalAlign
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
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
import com.someverse.presentation.components.Toast
import com.someverse.presentation.ui.theme.Black
import com.someverse.presentation.ui.theme.DescGray
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import com.someverse.presentation.ui.theme.SomeverseTheme
import com.someverse.presentation.ui.theme.withLetterSpacingPercent

@Composable
fun SignupNicknameScreen(
    onNextClick: () -> Unit = {},
    viewModel: SignupNicknameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    SignupNicknameScreenContent(
        onButtonClick = {
            if (uiState.isNicknameAvailable) {
                onNextClick()
            } else {
                viewModel.validateNickname()
            }
        },
        nickname = uiState.nickname,
        onValueChange = viewModel::onValueChange,
        errorMessage = uiState.errorMessage,
        isNicknameAvailable = uiState.isNicknameAvailable,
        deleteErrorMessage = viewModel::deleteErrorMessage
    )
}

@Composable
private fun SignupNicknameScreenContent(
    onButtonClick: () -> Unit,
    nickname: String,
    onValueChange: (String) -> Unit,
    errorMessage: String,
    isNicknameAvailable: Boolean,
    deleteErrorMessage: () -> Unit
) {
    // 이미지 로고
    val myId = "inlineLogo"
    val inlineContent = mapOf(
        myId to InlineTextContent(
            Placeholder(
                width = 100.sp,
                height = 20.sp,
                placeholderVerticalAlign = PlaceholderVerticalAlign.Top
            )
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_with_text),
                contentDescription = "SOMEVERSE 로고",
                modifier = Modifier.fillMaxSize()
            )
        }
    )

    val annotatedString = buildAnnotatedString {
        // 로고 삽입
        appendInlineContent(myId, "[logo]")
        append(" 가 처음이시군요!\n닉네임을 정해주세요.")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(Dimensions.screenPadding),
        ) {
            // 상단 타이틀
            SomeVerseTopBar(title = "회원가입")

            // 상단 여백
            Spacer(modifier = Modifier.height(Dimensions.space16))

            // 제목
            Text(
                text = annotatedString,
                inlineContent = inlineContent,
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

            Spacer(modifier = Modifier.height(Dimensions.space4))

            // 설명
            Text(
                text = "흥미로운 닉네임일수록 매칭율이 높아져요!",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = PretendardFontFamily,
                ).withLetterSpacingPercent(-2.5f),
                color = DescGray,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(Dimensions.space16))

            Column {
                // 닉네임 입력 필드
                TextField(
                    value = nickname,
                    onValueChange = onValueChange,
                    placeholder = {
                        Text(
                            text = "닉네임을 설정하세요",
                            color = Color(0xFFADB5BD)
                        )
                    },
                    leadingIcon = {
                        if (nickname.isNotEmpty()) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_my_profile),
                                contentDescription = null,
                                tint = Color(0xFFADB5BD),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(66.dp)
                        .clip(RoundedCornerShape(16.dp)),

                    // 에러 상태 설정 (메시지가 있으면 에러)
                    isError = errorMessage.isNotEmpty(),

                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color(0xFFEDF1F7),
                        unfocusedContainerColor = Color(0xFFEDF1F7),
                        errorContainerColor = Color(0xFFEDF1F7),
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (errorMessage.isNotEmpty()) Color.Red else Color(0xFF495057)
                    ),
                    singleLine = true
                )
                if (isNicknameAvailable) {
                    Text(
                        text = "사용 가능한 닉네임이에요.",
                        color = Color(0xFF4CAF50),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            // 에러 토스트
            if (errorMessage.isNotEmpty()) {
                Toast(
                    message = errorMessage,
                    onDismiss = { deleteErrorMessage() },
                    duration = 3000L,
                    isError = true,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // 페이지 인디케이터
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                repeat(5) { index ->
                    PageIndicator(isActive = index == 0)
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

            // 다음 버튼 (그라데이션 배경)
            GradientButton(
                text = "닉네임 결정하기",
                onClick = {
                    println("📦 '닉네임 결정하기' 버튼 클릭 -> 닉네임 중복 여부 검사")
                    onButtonClick()
                },
                enabled = nickname.isNotEmpty(),
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                        .padding(horizontal = Dimensions.space8),
            )

            Spacer(modifier = Modifier.height(Dimensions.space48))
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun SignupNicknameScreenPreView() {
    SomeverseTheme {
        SignupNicknameScreenContent(
            nickname = "썸버스유저",
            onValueChange = {},
            errorMessage = "",
            isNicknameAvailable = false,
            deleteErrorMessage = {},
            onButtonClick = {}
        )
    }
}