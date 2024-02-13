package com.wioletamwrobel.myartspace

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wioletamwrobel.myartspace.model.Album
import com.wioletamwrobel.myartspace.model.MyArtDao
import com.wioletamwrobel.myartspace.model.MyArtSpaceDao
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabase
import com.wioletamwrobel.myartspace.ui.theme.MyArtSpaceTheme
import com.wioletamwrobel.myartspace.ui.theme.Shapes
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.concurrent.thread

class MainActivity : ComponentActivity() {

    //private lateinit var viewModel: MyArtSpaceAppViewModel

    val viewModel by viewModel<MyArtSpaceAppViewModel>()
    private lateinit var uiState: State<MyArtSpaceUiState>
    //private lateinit var albumList: List<Album>

    private val database: MyArtSpaceDatabase by lazy {
        MyArtSpaceDatabase.getDatabase(this)
    }
    private val myArtSpaceDao: MyArtSpaceDao by lazy {
        database.getMyAlbumDao()
    }

    private val myArtDao: MyArtDao by lazy {
        database.getMyArtDao()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {

            MyArtSpaceTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    uiState = viewModel.uiState.collectAsState()
                    thread {
                        viewModel.updateAlbumListToDisplay(myArtSpaceDao.getAllAlbums())
                    }
                    Navigation()
                }
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        thread {
//            albumList = myArtSpaceDao.getAllAlbums()
//        }
//    }

//    @Composable
//    fun CreateViewModel() {
//
//    }

    @SuppressLint("ComposableDestinationInComposeScope")
    @Composable
    fun Navigation() {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = "login_sign_up_screen") {
            composable(route = "login_sign_up_screen") {
                LogInSignUpScreen(
                    onNavigationToSignUp = { navController.navigate("signup_dialog") },
                    onNavigationToLogIn = { navController.navigate("login_dialog") }
                )
            }
            composable(route = "signup_dialog") {
                Dialog.CreateDialog(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    title = stringResource(R.string.sign_up),
                    dialogText = { SignUpDialogText() },
                    onConfirmButtonClicked = {
                        navController.navigate("home_screen")
                    },
                    onDismissButtonClicked = { navController.navigate("login_sign_up_screen") }
                )
            }
            composable(route = "login_dialog") {
                Dialog.CreateDialog(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                    title = stringResource(R.string.log_in),
                    dialogText = { LogInDialogText() },
                    onConfirmButtonClicked = {
                        navController.navigate("home_screen")
                    },
                    onDismissButtonClicked = { navController.navigate("login_sign_up_screen") }
                )
            }
            composable(route = "home_screen") {
                HomeScreen(
                    uiState = uiState,
                    viewModel = viewModel,
                    myArtSpaceDao,
                    albumList = viewModel.albumListToDisplay,
                    navController = navController,
                    myArtDao = myArtDao
                )
            }
            composable(route = "art_card_screen") {
                ArtCardScreen(
                    albumId = viewModel.currentAlbumId,
                    viewModel = viewModel,
                    uiState = uiState,
                    navController = navController,
                    myArtDao = myArtDao,
                    artListSizeFromCurrentAlbum = viewModel.artAmountInCurrentAlbum
                )
            }
        }
    }


    @Composable
    fun LogInSignUpScreen(
        onNavigationToSignUp: () -> Unit,
        onNavigationToLogIn: () -> Unit,
    ) {

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
        ) {
            AppNameAndIcon()
            Text(
                text = stringResource(id = R.string.motto),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
            )
            ActionButton(
                onClick = {
                    onNavigationToSignUp()
                },
                text = stringResource(R.string.sign_up),
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_medium))
            )
            Text(
                text = stringResource(id = R.string.have_account),
                style = MaterialTheme.typography.bodySmall,
            )
            ActionButton(
                onClick = {
                    onNavigationToLogIn()
                },
                text = stringResource(R.string.log_in),
                modifier = Modifier
            )
        }
    }

    @Composable
    fun ActionButton(onClick: () -> Unit, text: String, modifier: Modifier) {
        Button(
            onClick = onClick, modifier = modifier.widthIn(min = 250.dp),
            shape = Shapes.small
        ) {
            Text(text = text)
        }
    }

    @Composable
    fun LogInDialogText() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.have_account),
                style = MaterialTheme.typography.titleMedium
            )
            Dialog.DialogTextField(
                value = "",
                labelText = stringResource(R.string.login),
                onValueChange = {}
            )
            Dialog.DialogTextField(
                value = "",
                labelText = stringResource(R.string.password),
                onValueChange = {}
            )
        }
    }

    @Composable
    fun SignUpDialogText() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.create_account),
                style = MaterialTheme.typography.titleMedium
            )
            Dialog.DialogTextField(
                value = "",
                labelText = stringResource(R.string.login),
                onValueChange = {}
            )
            Dialog.DialogTextField(
                value = "",
                labelText = stringResource(R.string.e_mail),
                onValueChange = {}
            )
            Dialog.DialogTextField(
                value = "",
                labelText = stringResource(R.string.password),
                onValueChange = {}
            )
        }
    }
}