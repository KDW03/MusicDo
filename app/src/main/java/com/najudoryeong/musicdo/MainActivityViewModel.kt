package com.najudoryeong.musicdo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.najudoryeong.musicdo.core.domain.usecase.GetUserDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


@HiltViewModel
class MainActivityViewModel @Inject constructor(getUserDataUseCase: GetUserDataUseCase) : ViewModel() {
    val uiState = getUserDataUseCase()
        .map(DoUiState::Success)
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = DoUiState.Loading
        )
}