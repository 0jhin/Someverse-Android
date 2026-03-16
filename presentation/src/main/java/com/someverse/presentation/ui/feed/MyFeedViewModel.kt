package com.someverse.presentation.ui.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.someverse.domain.usecase.feed.GetMyFeedsUseCase
import com.someverse.domain.usecase.feed.InactivateFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedViewModel @Inject constructor(
    private val getMyFeedsUseCase: GetMyFeedsUseCase,
    private val inActivateFeedUseCase: InactivateFeedUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyFeedUiState())
    val uiState: StateFlow<MyFeedUiState> = _uiState.asStateFlow()

    init {
        loadMyFeeds()
    }

    fun loadMyFeeds() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getMyFeedsUseCase()
                .onSuccess { feeds ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            feeds = feeds,
                            error = null,
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error occurred",
                        )
                    }
                }
        }

    }

    // 팝업 열기
    fun openPopup(type: PopupType, feedId: Long) {
        _uiState.update {
            it.copy(currentPopup = type, selectedFeedId = feedId)
        }
    }

    // 팝업 닫기 (공통)
    fun dismissPopup() {
        _uiState.update {
            it.copy(currentPopup = null, selectedFeedId = null)
        }
    }

    fun onEditClick() {
        //TODO: 수정 로직 실행 후 닫기
        dismissPopup()
    }

    fun onDeleteConfirm() {
        val feedId = _uiState.value.selectedFeedId ?: return

        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            inActivateFeedUseCase(feedId)
                .onSuccess { success ->
                    _uiState.update { it ->
                        it.copy(
                            isLoading = false,
                            error = null,
                            feeds = it.feeds.filter { it.id != feedId },
                            selectedFeedId = null,
                            currentPopup = null
                        )
                    }
                }.onFailure { exception ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = exception.message ?: "Unknown error occurred",
                        )
                    }
                }
        }
    }
}
