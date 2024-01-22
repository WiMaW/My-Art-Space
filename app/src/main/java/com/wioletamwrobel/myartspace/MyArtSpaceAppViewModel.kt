package com.wioletamwrobel.myartspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.wioletamwrobel.myartspace.model.Album
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyArtSpaceAppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyArtSpaceUiState())
    val uiState: StateFlow<MyArtSpaceUiState> = _uiState

    var userInputNewAlbumTitle by mutableStateOf("")
        private set
    var userInputNewAlbumDescription by mutableStateOf("")
        private set
    var userInputNewAlbumCreationDate by mutableStateOf("")
        private set
    var userInputNewAlbumImage by mutableStateOf("")
        private set

    fun updateUserInputNewAlbumTitle(newAlbumTitle: String){
        userInputNewAlbumTitle = newAlbumTitle
    }
    fun updateUserInputNewAlbumDescription(newAlbumDescription: String) {
        userInputNewAlbumDescription = newAlbumDescription
    }
    fun updateUserInputNewAlbumCreationDate(newAlbumCreationDate: String) {
        userInputNewAlbumCreationDate = newAlbumCreationDate
    }
    fun updateUserInputNewAlbumImage(newAlbumImage: String) {
        userInputNewAlbumImage = newAlbumImage
    }

    fun updateAlbumList() {

    }

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
    val navigationBarItemClicked: Int = 0,

    var albumTitle:String = "",
    var albumDescription: String = "",
    var albumImage: String = "",
    var albumCreationDate: String = ""
)

