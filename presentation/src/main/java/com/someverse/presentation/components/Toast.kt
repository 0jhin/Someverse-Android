package com.someverse.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.someverse.presentation.R
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import kotlinx.coroutines.delay

/**
 * Toast 메시지를 표시하는 Composable
 * @param message 표시할 메시지
 * @param onDismiss Toast가 사라질 때 호출되는 콜백
 * @param duration Toast 표시 시간 (밀리초)
 * @param isError 에러 메시지 여부 (true: 빨간색, false: 검은색)
 */
@Composable
fun Toast(
    modifier: Modifier = Modifier,
    message: String,
    onDismiss: () -> Unit,
    duration: Long = 3000L,
    isError: Boolean = true,
) {
    LaunchedEffect(message) {
        if (message.isNotEmpty()) {
            delay(duration)
            onDismiss()
        }
    }

    if (message.isNotEmpty()) {
        Box(
            modifier =
                modifier
                    .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Surface(
                modifier = Modifier
                    .width(343.dp)
                    .shadow(
                        elevation = 16.dp,
                        shape = RoundedCornerShape(16.dp),
                        clip = false,
                        spotColor = Color(0x0DE25061),
                    ),
                shape = RoundedCornerShape(16.dp),
                color = Color.White,
            ) {
                Box(
                    modifier = Modifier.padding(
                        vertical = Dimensions.space4,
                        horizontal = Dimensions.space16
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .padding(end = 24.dp),
                    ) {
                        if (isError) {
                            // 에러 아이콘
                            Icon(
                                painter = painterResource(id = R.drawable.ic_error_outline),
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(end = 16.dp)
                                    .size(20.dp)
                                    .align(Alignment.Top),
                                tint = Color(0xFFE25061)
                            )
                        }

                        Text(
                            text = message,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                fontFamily = PretendardFontFamily,
                            ),
                            // 에러일 땐 붉은색, 성공일 땐 보라색 텍스트 색상 분기
                            color = if (isError) Color(0xFFE25061) else Color(0xFF7250C9),
                            textAlign = TextAlign.Start,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun ToastPreView() {
    Toast(
        message = "정보 수정이 완료되었어요!\n" +
                "프로필 사진 수정은 관리자 승인 후 반영돼요:)",
        onDismiss = {},
        isError = false
    )
}

@Preview(showBackground = false)
@Composable
fun ErrorToastPreView() {
    Toast(
        message = "필수 프로필 사진 1장은 반드시 유지되어야 해요:)",
        onDismiss = {},
        isError = true
    )
}

@Preview(showBackground = false)
@Composable
fun ErrorOneLineToastPreView() {
    Toast(
        message = "이미 사용 중인 닉네임이에요.",
        onDismiss = {},
        isError = true
    )
}