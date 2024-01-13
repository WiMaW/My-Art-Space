package com.wioletamwrobel.myartspace

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyArtSpaceAppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyArtSpaceUiState())
    val uiState: StateFlow<MyArtSpaceUiState> = _uiState

    fun navigateToBarItemDialog(barItemNumber: Int) {
        _uiState.update {
            it.copy(navigationBarItemClicked = barItemNumber)
        }
    }

    fun navigateToHomeScreenFromDialog() {
        _uiState.update {
            it.copy(navigationBarItemClicked = 0)
        }
    }
}

data class MyArtSpaceUiState(
    val isLogInButtonClicked: Boolean = false,
    val isSignUpButtonClicked: Boolean = false,
    val isConfirmLogInSignUpButtonClicked: Boolean = false,
    val navigationBarItemClicked: Int = 0
)

