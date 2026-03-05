package com.someverse.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.someverse.presentation.R
import com.someverse.presentation.ui.theme.DescGray
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.PretendardFontFamily
import com.someverse.presentation.ui.theme.withLetterSpacingPercent

@Composable
fun SomeVerseTopBar(
    modifier: Modifier = Modifier,
    textTitle: String? = null,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcons: List<@Composable () -> Unit> = emptyList()
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(height = 52.dp)
            .background(color = Color(0xffFAFAFA))
            .padding(horizontal = 30.dp)
    ) {
        // 좌측 아이콘
        if (leadingIcon != null) {
            Box(
                modifier = Modifier
                    .align(Alignment.CenterStart)
            ) {
                leadingIcon()
            }
        }

        // 텍스트 제목
        if (textTitle != null) {
            Text(
                text = textTitle,
                style = MaterialTheme.typography.titleMedium
                    .copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp,
                        lineHeight = 32.sp,
                        fontFamily = PretendardFontFamily,
                    ).withLetterSpacingPercent(-2.5f),
                color = DescGray,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // 이미지 제목
        if (textTitle == null) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo_with_text),
                contentDescription = "SOMEVERSE 로고",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(start = if (leadingIcon != null) 60.dp else Dimensions.space16)
                    .width(108.dp)
                    .height(20.dp)
            )
        }

        // 우측 아이콘 그룹 (Row 배치)
        if (trailingIcons.isNotEmpty()) {
            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                verticalAlignment = Alignment.CenterVertically
            ) {
                trailingIcons.forEach { icon ->
                    icon()
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = false)
fun SomeVerseTopBarPreview() {
    val iconColor = Color(0xFF9098A6)
    Column {
        SomeVerseTopBar(
            textTitle = "취향 입력",
            leadingIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dismiss),
                        contentDescription = "뒤로가기",
                        tint = iconColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
            },
            trailingIcons = listOf(
                {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색",
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
            )
        )

        Spacer(modifier = Modifier.height(Dimensions.space12))

        SomeVerseTopBar(
            leadingIcon = {
                IconButton(
                    onClick = {}
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_dismiss),
                        contentDescription = "뒤로가기",
                        tint = iconColor,
                        modifier = Modifier.size(size = 28.dp)
                    )
                }
            },
            trailingIcons = listOf(
                {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = "검색",
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_image),
                            contentDescription = "설정",
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        )
    }
}