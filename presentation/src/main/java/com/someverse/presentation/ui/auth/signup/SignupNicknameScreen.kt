package com.someverse.presentation.ui.auth.signup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.someverse.presentation.components.GradientButton
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
        onNextClick = {
            viewModel.submitNickname(onSuccess = onNextClick)
        },
        nickname = uiState.nickname,
        onValueChange = viewModel::onValueChange,
        errorMessage = uiState.errorMessage,
        deleteErrorMessage = viewModel::deleteErrorMessage
    )
}

@Composable
private fun SignupNicknameScreenContent(
    onNextClick: () -> Unit = {},
    nickname: String,
    onValueChange: (String) -> Unit,
    errorMessage: String,
    deleteErrorMessage: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 상단 타이틀
            Text(
                text = "회원가입",
                style =
                    MaterialTheme.typography.titleMedium
                        .copy(
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 20.sp,
                            lineHeight = 32.sp,
                            textAlign = TextAlign.Center,
                            fontFamily = PretendardFontFamily,
                        ).withLetterSpacingPercent(-2.5f),
                color = DescGray,
                modifier = Modifier.fillMaxWidth(),
            )

            Column(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(Dimensions.screenPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // 상단 여백
                Spacer(modifier = Modifier.height(Dimensions.space24))

                // 제목
                Text(
                    text = "SOMEVERSE가 처음이시군요!\n닉네임을 정해주세요.",
                    style = MaterialTheme.typography.displaySmall,
                    textAlign = TextAlign.Start,
                    color = Black,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(Dimensions.space12))

                // 설명
                Text(
                    text = "흥미로운 닉네임일수록 매칭율이 높아져요!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = DescGray,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth(),
                )

                Spacer(modifier = Modifier.height(Dimensions.space12))

                // 닉네임 입력 필드
                TextField(
                    value = nickname,
                    onValueChange = onValueChange
                )


                Spacer(modifier = Modifier.weight(1f))

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
                        onNextClick()
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

        // Toast 메시지 (하단에 표시)
        Toast(
            message = errorMessage,
//            onDismiss = { /* 에러 메시지 자동 사라짐 */ },
            onDismiss = { deleteErrorMessage() },
            duration = 3000L,
            isError = true,
            modifier =
                Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 100.dp),
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
fun SignupNicknameScreenPreView() {
    SomeverseTheme {
        SignupNicknameScreenContent(
            nickname = "썸버스유저",
            onValueChange = {},
            onNextClick = {},
            errorMessage = "",
            deleteErrorMessage = {}
        )
    }
}