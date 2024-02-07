package com.wioletamwrobel.myartspace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wioletamwrobel.myartspace.model.Album
import com.wioletamwrobel.myartspace.model.Art
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyArtSpaceAppViewModel(
    private val myArtSpaceDatabaseRepository: MyArtSpaceDatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyArtSpaceUiState())
    val uiState: StateFlow<MyArtSpaceUiState> = _uiState

    //AlertDialog Add Album User Inputs
    var userInputNewAlbumTitle by mutableStateOf("")

    var userInputNewAlbumDescription by mutableStateOf("")

    var userInputNewAlbumCreationDate by mutableStateOf("")

    var userInputNewAlbumImage by mutableStateOf("")

    fun updateUserInputNewAlbumTitle(newAlbumTitle: String) {
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
        _uiState.update {
            it.copy(isAlbumPhotoAdded = true)
        }
    }

    fun clearUserInputNewAlbumFields() {
        userInputNewAlbumTitle = ""
        userInputNewAlbumDescription = ""
        userInputNewAlbumCreationDate = ""
        userInputNewAlbumImage = ""
        _uiState.update {
            it.copy(isAlbumPhotoAdded = false)
        }
    }

    //Updating albumList to display
    var albumListToDisplay: List<Album> by mutableStateOf(emptyList())

    fun updateAlbumListToDisplay(
        albumList: List<Album>
    ) {
        viewModelScope.launch {
            try {
                albumListToDisplay = albumList.toMutableList()
            } catch (e: Exception) {

            }
        }
    }

    //Sending album id between screens
    var currentAlbumId: Long by mutableLongStateOf(0)
    var artAmountInCurrentAlbum: Int by mutableIntStateOf(0)


    fun updateAlbumId(albumId: Long) {
        currentAlbumId = albumId
    }

    fun updateArtAmountInCurrentAlbum(artListSize: Int) {
        artAmountInCurrentAlbum = artListSize
    }

    //AlertDialog Add Art User Inputs
    var userInputNewArtTitle by mutableStateOf("")
        private set
    var userInputNewArtMethod by mutableStateOf("")
        private set
    var userInputNewArtDate by mutableStateOf("")
        private set
    var userInputNewArtImage by mutableStateOf("")

    fun updateUserInputNewArtTitle(newArtTitle: String) {
        userInputNewArtTitle = newArtTitle
    }

    fun updateUserInputNewArtMethod(newArtMethod: String) {
        userInputNewArtMethod = newArtMethod
    }

    fun updateUserInputNewArtDate(newArtDate: String) {
        userInputNewArtDate = newArtDate
    }

    fun updateUserInputNewArtImage(newArtImage: String) {
        userInputNewArtImage = newArtImage
        _uiState.update {
            it.copy(isArtPhotoAdded = true)
        }
    }

    fun clearUserInputNewArtFields() {
        userInputNewArtTitle = ""
        userInputNewArtMethod = ""
        userInputNewArtDate = ""
        userInputNewArtImage = ""
        _uiState.update {
            it.copy(isArtPhotoAdded = false)
        }
    }

    //saving current album art list to display in ArtCardScreen
    var artListInCurrentAlbum: List<Art> by mutableStateOf(emptyList())

    fun updateCurrentAlbumArtListToDisplay(artList: List<Art>) {
        viewModelScope.launch {
            try {
                artListInCurrentAlbum = artList.toMutableList()
            } catch (e: Exception) {

            }
        }
    }

    // navigation functions
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

    fun navigateToAddArtAlertDialog() {
        _uiState.update {
            it.copy(isAddArtButtonClicked = true)
        }
    }

    fun navigateToEditAlbumAlertDialog() {
        _uiState.update {
            it.copy(isEditAlbumButtonClicked = true)
        }
    }

    fun navigateToHomeScreenFromEditAlbumAlertDialog() {
        _uiState.update {
            it.copy(isEditAlbumButtonClicked = false)
        }
    }

    fun navigateToArtCardScreenFromAlertDialog() {
        _uiState.update {
            it.copy(isAddArtButtonClicked = false)
        }
    }
}

data class MyArtSpaceUiState(
    val isLogInButtonClicked: Boolean = false,
    val isSignUpButtonClicked: Boolean = false,
    val isAlbumPhotoAdded: Boolean = false,
    val isArtPhotoAdded: Boolean = false,
    val isAddArtButtonClicked: Boolean = false,
    val isEditAlbumButtonClicked: Boolean = false,
    val navigationBarItemClicked: Int = 0,
)

