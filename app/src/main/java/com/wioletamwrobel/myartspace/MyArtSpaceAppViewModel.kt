package com.wioletamwrobel.myartspace

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wioletamwrobel.myartspace.model.Album
import com.wioletamwrobel.myartspace.model.Art
import com.wioletamwrobel.myartspace.model.MyArtDao
import com.wioletamwrobel.myartspace.model.MyArtSpaceDao
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabase
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabaseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MyArtSpaceAppViewModel(
    private val myArtSpaceDatabaseRepository: MyArtSpaceDatabaseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MyArtSpaceUiState())
    val uiState: StateFlow<MyArtSpaceUiState> = _uiState


    //AlertDialog Add Album User Inputs
    var userInputNewAlbumTitle by mutableStateOf("")
        private set
    var userInputNewAlbumDescription by mutableStateOf("")
        private set
    var userInputNewAlbumCreationDate by mutableStateOf("")
        private set
    var userInputNewAlbumImage by mutableStateOf("")

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
        _uiState.update {
            it.copy(isAlbumPhotoAdded = true)
        }
    }

    //AlertDialog Add Art User Inputs
    var userInputNewArtTitle by mutableStateOf("")
        private set
    var userInputNewArtMethod by mutableStateOf("")
        private set
    var userInputNewArtDate by mutableStateOf("")
        private set
    var userInputNewArtImage by mutableStateOf("")

    fun updateUserInputNewArtTitle(newArtTitle: String){
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

    //Sending album id between screens
    var currentAlbumId: Long by mutableLongStateOf(0)
        private set
    var artAmountInCurrentAlbum: Int? by mutableStateOf(0)
        private set

    fun updateAlbumId(albumId: Long) {
        currentAlbumId = albumId
    }

    fun updateArtAmountInCurrentAlbum(artListSize: Int) {
        artAmountInCurrentAlbum = artListSize
    }

    //saving current album art list to display in ArtCardScreen
    var artListInCurrentAlbum by mutableStateOf(emptyList<Art>())

    fun updateCurrentAlbumArtListToDisplay(
       // albumId: Long
        artList: List<Art>
    ) {
        viewModelScope.launch {
            try {
                artListInCurrentAlbum = artList.toMutableList()
                    //myArtSpaceDatabaseRepository.getAllArtsFromCurrentAlbum(currentAlbumId).toMutableList()
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
            it.copy(isAddArtButtonClicked= true)
        }
    }

    fun navigateToArtCardScreenFromAlertDialog() {
        _uiState.update {
            it.copy(isAddArtButtonClicked= false)
        }
    }
}

data class MyArtSpaceUiState(
    val isLogInButtonClicked: Boolean = false,
    val isSignUpButtonClicked: Boolean = false,
    val isAlbumPhotoAdded: Boolean = false,
    val isArtPhotoAdded: Boolean = false,
    val isAddArtButtonClicked: Boolean = false,
    val navigationBarItemClicked: Int = 0,
)

