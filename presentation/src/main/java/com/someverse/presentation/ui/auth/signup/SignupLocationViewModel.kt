package com.someverse.presentation.ui.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.someverse.domain.model.Location
import com.someverse.domain.usecase.onboarding.GetAddressListUseCase
import com.someverse.domain.usecase.onboarding.SubmitAddressUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * 위치 정보 입력 화면 ViewModel
 */
@HiltViewModel
class SignupLocationViewModel
@Inject
constructor(
    private val submitAddressUseCase: SubmitAddressUseCase,
    private val getAddressListUseCase: GetAddressListUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SignupLocationUiState())
    val uiState: StateFlow<SignupLocationUiState> = _uiState.asStateFlow()

    init {
        // 처음 시작할 때 API에서 가능한 위치 목록을 가져오기
        loadAddressList()
    }

    fun toggleDropdown() {
        _uiState.update { it.copy(isDropdownExpanded = !it.isDropdownExpanded) }
    }

    // 주소 목록 가져오기 - 실제 서비스에서는 API를 통해 받아올 예정
    private fun loadAddressList() {
        viewModelScope.launch {
            getAddressListUseCase()
                .onSuccess { locations ->
                    _uiState.update { it ->
                        // API에서 받아온 데이터로 지역 정보 및 구/군 맵 구성
                        val regions = locations.map { it.city }.distinct()
                        val districtMap =
                            locations
                                .groupBy { it.city }
                                .mapValues { entry -> entry.value.map { it.district } }

                        it.copy(
                            // 사용 가능한 전체 위치 목록 저장
                            regions = regions,
                            districtMap = districtMap,
                        )
                    }
                }.onFailure { error ->
                    _uiState.update {
                        it.copy(errorMessage = "지역 정보를 불러오는 중 오류가 발생했습니다.")
                    }
                }
        }
    }

    // 위치 선택 처리 (현재 단계에 따라 다르게 처리)
    fun selectItem(item: String) {
        val currentState = _uiState.value

        // 현재 선택하려는 것이 '도시(City)'인 경우
        if (currentState.regions.contains(item)) {
            if (currentState.selectedCity == item) {
                // 이미 선택된 도시를 다시 누르면 선택 해제
                _uiState.update {
                    it.copy(
                        selectedCity = null,
                        selectionStep = SelectionStep.CITY
                    )
                }
            } else {
                // 다른 도시를 누르면 해당 도시로 교체 및 구/군 단계로 변경
                _uiState.update {
                    it.copy(
                        selectedCity = item,
                        selectionStep = SelectionStep.DISTRICT
                    )
                }
            }
        }
        // 현재 선택하려는 것이 '구/군(District)'인 경우
        else {
            val selectedCity = currentState.selectedCity ?: return
            addLocationSelection(selectedCity, item)
        }
    }

    // 위치 선택 추가
    private fun addLocationSelection(
        city: String,
        district: String,
    ) {
        val currentState = _uiState.value
        val currentSelections = currentState.selectedLocations.toMutableList()
        val newSelection = Location(city, district)

        // 같은 도시의 기존 선택 확인 및 제거 (중복 방지를 위한 2중 안전장치)
        currentSelections.removeAll { it.city == city }

        // 완전히 동일한 선택(도시+구/군)이 있는지 한 번 더 확인
        val exactMatch =
            currentSelections.find {
                it.city == city && it.district == district
            }

        if (exactMatch != null) {
            // 이미 완전히 동일한 선택이 있으면 선택 취소하고 도시 선택 단계로 돌아감
            _uiState.update {
                it.copy(
                    selectedCity = null,
                    selectionStep = SelectionStep.CITY,
                    isDropdownExpanded = false,
                )
            }
            return
        }

        // 최대 선택 개수 확인
        if (currentSelections.size >= currentState.maxLocationSelection) {
            return
        }

        // 새 위치 추가하고 도시 선택 단계로 돌아감
        currentSelections.add(newSelection)
        _uiState.update {
            it.copy(
                selectedLocations = currentSelections.take(currentState.maxLocationSelection), // 최대 개수 제한 한번 더 확인
                selectedCity = null,
                selectionStep = SelectionStep.CITY,
                isDropdownExpanded = false,
            )
        }
    }

    // 선택한 위치 제거
    fun removeLocation(selection: Location) {
        val currentSelections = _uiState.value.selectedLocations.toMutableList()
        currentSelections.remove(selection)
        _uiState.update { it.copy(selectedLocations = currentSelections) }
    }

    // 위치 정보 제출
    fun submitLocation() {
        val currentState = _uiState.value
        val selectedLocations = currentState.selectedLocations

        if (selectedLocations.isEmpty()) return

        _uiState.update { it.copy(isLoading = true) }
        println("🔴 위치 정보 제출 시작")

        viewModelScope.launch {
            try {
                // 선택된 위치를 제출
                submitAddressUseCase(selectedLocations)
                    .onSuccess { user ->
                        println("✅ 위치 제출 성공 - 화면 이동 시킴 [canProceed=true]")
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                canProceed = true,
                            )
                        }
                    }.onFailure { exception ->
                        println("❌ 위치 제출 실패: ${exception.message}")
                        _uiState.update { state ->
                            state.copy(
                                isLoading = false,
                                errorMessage =
                                    exception.message
                                        ?: "위치 정보 저장 중 오류가 발생했습니다.",
                            )
                        }
                    }
            } catch (e: Exception) {
                println("❌ 위치 제출 예외 발생: ${e.message}")
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "오류가 발생했습니다. 다시 시도해주세요.",
                    )
                }
            }
        }
    }

    // 추가 작업 후 canProceed 상태 초기화
    fun resetProceedState() {
        _uiState.update { it.copy(canProceed = false) }
        println("canProceed 상태 초기화 (false)")
    }
}
