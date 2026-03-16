package com.someverse.presentation.ui.feed

import com.someverse.domain.model.Feed

enum class PopupType {
    EDIT_SHEET,
    DELETE_DIALOG
}

data class MyFeedUiState(
    val isLoading: Boolean = false,
    val feeds: List<Feed> = emptyList(),
    val error: String? = null,
    val currentPopup: PopupType? = null, // 현재 열려있는 팝업 타입
    val selectedFeedId: Long? = null
)