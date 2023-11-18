package com.najudoryeong.musicdo

import com.najudoryeong.musicdo.core.model.UserData

interface DoUiState {
    data object Loading : DoUiState
    data class Success(val userData: UserData) : DoUiState
}