package com.someverse.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.someverse.domain.model.Feed
import com.someverse.presentation.R
import com.someverse.presentation.components.DialogType
import com.someverse.presentation.components.SomeVerseDialog
import com.someverse.presentation.components.SomeVerseTopBar
import com.someverse.presentation.components.TopBarTitle
import com.someverse.presentation.ui.theme.Dimensions
import com.someverse.presentation.ui.theme.SomeverseTheme

@Composable
fun MyFeedScreen(
    viewModel: MyFeedViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    MyFeedScreenContent(
        feeds = uiState.feeds,
        currentPopup = uiState.currentPopup,
        onMoreClick = { feedId -> viewModel.openPopup(PopupType.EDIT_SHEET, feedId) },
        onDismiss = viewModel::dismissPopup,
        onEdit = viewModel::onEditClick,
        onDeleteRequest = {
            // 현재 저장된 ID를 유지한 채 팝업 타입만 변경
            viewModel.openPopup(PopupType.DELETE_DIALOG, uiState.selectedFeedId ?: -1L)
        },
        onDeleteConfirm = viewModel::onDeleteConfirm
    )
}

@Composable
fun MyFeedScreenContent(
    modifier: Modifier = Modifier,
    feeds: List<Feed> = emptyList(),
    currentPopup: PopupType? = null,
    onMoreClick: (Long) -> Unit,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDeleteRequest: () -> Unit,
    onDeleteConfirm: () -> Unit
) {
    val iconColor = Color(0xFF9098A6)

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(top = Dimensions.topPadding)
                .background(Color(0xFFFAFAFA))
                .blur(if (currentPopup != null) 10.dp else 0.dp)
        ) {
            SomeVerseTopBar(
                title = TopBarTitle.Text("내 피드"),
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
                trailingIcons = {
                    IconButton(onClick = {}) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_add_image),
                            contentDescription = "추가",
                            tint = iconColor,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                contentPadding = PaddingValues(vertical = 20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(feeds) { feed ->
                    MyFeedCard(feed = feed, onMoreClick = { onMoreClick(feed.id) })
                }
            }
        }


        // 팝업 타입에 따른 분기 처리
        when (currentPopup) {
            PopupType.EDIT_SHEET -> {
                EditDeleteCustomBottomSheet(
                    onDismiss = onDismiss,
                    onEditClick = onEdit,
                    onDeleteClick = onDeleteRequest
                )
            }

            PopupType.DELETE_DIALOG -> {
                SomeVerseDialog(
                    title = "이 피드를 정말 삭제하시겠어요?",
                    description = "삭제한 피드는 다시 볼 수 없어요.",
                    type = DialogType.DANGER,
                    primaryButtonText = "삭제하기",
                    secondaryButtonText = "취소",
                    onPrimaryClick = onDeleteConfirm,
                    onDismissRequest = onDismiss,
                    onSecondaryClick = onDismiss
                )
            }

            null -> { /* 아무것도 표시하지 않음 */
            }
        }
    }
}

@Composable
fun MyFeedCard(feed: Feed, onMoreClick: (Long) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(112.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(Color(0xFFFFFFFF))
            .padding(vertical = 22.dp)
            .padding(start = 20.dp, end = 28.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.Top
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 포스터
                AsyncImage(
                    model = feed.moviePosterPath,
                    contentDescription = "영화 포스터",
                    modifier = Modifier
                        .size(width = 48.dp, height = 69.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(Color.LightGray), // 로딩 전 배경색
                    contentScale = ContentScale.Crop, // 영역에 맞게 이미지 자르기
                    error = painterResource(id = R.drawable.ic_cancel_circle), // 에러 시 보여줄 이미지
                    placeholder = painterResource(id = R.drawable.ic_add_image) // 로딩 중 보여줄 이미지
                )

                Spacer(modifier = Modifier.width(Dimensions.space12))

                // 텍스트 영역
                Column(
                    modifier = Modifier, verticalArrangement = Arrangement.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = feed.movieTitle,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(modifier = Modifier.width(Dimensions.space4))

                        Text(
                            text = "(${feed.movieReleaseDate.take(4)})",
                            fontSize = 13.sp,
                            color = Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.height(Dimensions.space8))

                    Text(
                        text = feed.content,
                        fontSize = 14.sp,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Spacer(modifier = Modifier.width(9.dp))

            // 더보기 버튼
            Icon(
                painter = painterResource(id = R.drawable.ic_more_vert),
                contentDescription = "메뉴",
                tint = Color(0xFF9098A6),
                modifier = Modifier
                    .size(16.dp)
                    .clickable(
                        indication = null, // Disable the ripple effect
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        onMoreClick(feed.id)
                    }
            )
        }
    }
}

@Composable
fun EditDeleteCustomBottomSheet(
    onDismiss: () -> Unit, onEditClick: () -> Unit, onDeleteClick: () -> Unit
) {
    // 배경
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.3f))
            .clickable(
                indication = null, // Disable the ripple effect
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onDismiss()
            }
    ) {
        // 바텀 시트
        Box(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(horizontal = 16.dp)
                .height(120.dp)
                .background(Color.White, RoundedCornerShape(16.dp))
                .align(Alignment.BottomCenter)
        ) {
            // 아이템 배치
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                // 수정하기 아이템
                BottomSheetItem(
                    icon = Icons.Default.Edit,
                    text = "수정하기",
                    onClick = {
                        onDismiss()
                        onEditClick()
                    }
                )

                Spacer(Modifier.height(Dimensions.space8))

                // 구분선
                HorizontalDivider(
                    thickness = 0.5.dp,
                    color = Color(0xFFEBEFF5)
                )

                Spacer(Modifier.height(Dimensions.space8))

                // 삭제하기 아이템
                BottomSheetItem(
                    icon = Icons.Default.Delete,
                    text = "삭제하기",
                    onClick = {
                        onDismiss()
                        onDeleteClick()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomSheetItem(
    icon: ImageVector, text: String, onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clickable(
                indication = null, // Disable the ripple effect
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onClick()
            }
            .padding(start = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = Color.Gray)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, fontSize = 16.sp)
    }
}

@Composable
@Preview(showBackground = true, name = "내 피드 화면 미리보기")
fun MyFeedScreenPreview() {
    SomeverseTheme {
        MyFeedScreenContent(
            feeds = listOf(
                Feed(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImage = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                Feed(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImage = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
                Feed(
                    id = 1,
                    content = "오늘 본 인터스텔라 정말 최고였어요!",
                    nickName = "보름달투",
                    profileImage = "https://example.com/image1.jpg",
                    movieId = 157336,
                    movieTitle = "인터스텔라",
                    movieOverview = "우주를 배경으로 한 SF 대작",
                    moviePosterPath = "path/to/poster.jpg",
                    movieReleaseDate = "2014-11-07",
                    createdAt = "2024-12-22T10:00:00"
                ),
            ),
            onMoreClick = {},
            onDismiss = {},
            onEdit = {},
            onDeleteRequest = {},
            onDeleteConfirm = {},
        )
    }
}

@Composable
@Preview(showBackground = true, name = "피드 아이템 개별 미리보기")
fun MyFeedCardPreview() {
    MyFeedCard(
        feed = Feed(
            id = 1,
            content = "오늘 본 인터스텔라 정말 최고였어요!",
            nickName = "보름달투",
            profileImage = "https://example.com/image1.jpg",
            movieId = 157336,
            movieTitle = "인터스텔라",
            movieOverview = "우주를 배경으로 한 SF 대작",
            moviePosterPath = "path/to/poster.jpg",
            movieReleaseDate = "2014-11-07",
            createdAt = "2024-12-22T10:00:00"
        ),
        onMoreClick = {}
    )
}