package com.someverse.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import com.someverse.presentation.ui.theme.withLetterSpacingPercent

enum class DialogType(
    val primaryButtonColor: Color,
    val primaryButtonTextColor: Color,
    val secondaryButtonColor: Color,
    val secondaryButtonTextColor: Color
) {
    // 안내, 참여, 포인트 적립 등 (보라색 계열)
    INFO(
        primaryButtonColor = Color(0xFF7250C9), // 보라색
        primaryButtonTextColor = Color.White,
        secondaryButtonColor = Color(0xFFD5CBEF), // 연보라
        secondaryButtonTextColor = Color(0xFF616772)
    ),

    // 삭제, 탈퇴, 신고 등 (빨간색 계열)
    DANGER(
        primaryButtonColor = Color(0xFFE25061), // 빨간색
        primaryButtonTextColor = Color.White,
        secondaryButtonColor = Color(0xFFF7CBD0), // 연분홍
        secondaryButtonTextColor = Color(0xFF616772)

    )
}

@Composable
fun SomeVerseDialog(
    type: DialogType = DialogType.INFO,
    title: String,
    description: String? = null,
    primaryButtonText: String,
    secondaryButtonText: String = "나중에",
    onPrimaryClick: () -> Unit,
    onSecondaryClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
            modifier = Modifier
                .width(312.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.Start
            ) {
                // 제목
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        lineHeight = 30.sp,
                        fontFamily = PretendardFontFamily,
                    ).withLetterSpacingPercent(-2.5f),
                    color = Color(0xFF3C404E)
                )


                // 설명 (있을 경우에만 표시)
                if (description != null) {
                    Spacer(modifier = Modifier.height(Dimensions.space8))

                    val fontSize = 16.sp

                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = PretendardFontFamily,
                            fontWeight = FontWeight.Medium,
                            fontSize = fontSize,
                            lineHeight = fontSize * 1.2f,
                            letterSpacing = fontSize * -0.025f,
                            lineHeightStyle = LineHeightStyle(
                                alignment = LineHeightStyle.Alignment.Center,
                                trim = LineHeightStyle.Trim.None
                            )
                        ),
                        color = Color(0xFF656974)
                    )
                } else {
                    Spacer(modifier = Modifier.height(Dimensions.space8))
                    Spacer(modifier = Modifier.height(38.dp))
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 버튼 영역
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 보조 버튼 (취소, 나중에 등)
                    Box(
                        modifier = Modifier
                            .width(128.dp)
                            .height(48.dp)
                            .background(
                                color = type.secondaryButtonColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onSecondaryClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = secondaryButtonText,
                            color = type.secondaryButtonTextColor
                        )
                    }

                    // 주요 버튼 (추가하기, 삭제하기 등)
                    Box(
                        modifier = Modifier
                            .width(128.dp)
                            .height(48.dp)
                            .background(
                                color = type.primaryButtonColor,
                                shape = RoundedCornerShape(16.dp)
                            )
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                onPrimaryClick()
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = primaryButtonText,
                            color = type.primaryButtonTextColor
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InfoDialog() {
    SomeVerseDialog(
        type = DialogType.INFO,
        title = "마포구보안관2님에게\n" +
                "대화를 신청하시겠어요?",
        description = "무료 대화 신청은 1일 1회만 가능해요.\n" +
                "(추가 대화신청 시 루미 포인트 5개 필요)",
        primaryButtonText = "대화신청",
        secondaryButtonText = "취소",
        onPrimaryClick = { /* 로직 */ },
        onSecondaryClick = { /* 창 닫기 */ },
        onDismissRequest = { /* 외부 클릭 시 */ }
    )
}

@Preview(showBackground = true)
@Composable
fun DangerDialog() {
    SomeVerseDialog(
        type = DialogType.DANGER,
        title = "정말 로그아웃 하시겠어요?",
        primaryButtonText = "로그아웃",
        secondaryButtonText = "취소",
        onPrimaryClick = { /* 로직 */ },
        onSecondaryClick = { /* 취소 */ },
        onDismissRequest = { /* 외부 클릭 시 */ }
    )
}