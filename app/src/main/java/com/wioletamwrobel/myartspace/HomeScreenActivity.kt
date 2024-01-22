package com.wioletamwrobel.myartspace

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.sharp.Create
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.wioletamwrobel.myartspace.model.Album
import com.wioletamwrobel.myartspace.model.MyArtSpaceDao
import kotlin.concurrent.thread


//class HomeScreenActivity : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContent {
//            MyArtSpaceTheme {
//                // A surface container using the 'background' color from the theme
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//                    HomeScreen()
//                }
//            }
//        }
//    }
//}

@Composable
fun HomeScreen(
    uiState: State<MyArtSpaceUiState>,
    viewModel: MyArtSpaceAppViewModel,
    myArtSpaceDao: MyArtSpaceDao,
    albumList: List<Album>,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = Modifier
            .padding(vertical = dimensionResource(id = R.dimen.padding_medium))
            .fillMaxSize()
    ) {
        AppNameAndIcon()
        AlbumsLazyColumn(albumList = albumList)
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(viewModel = viewModel)
    }
    CreateDialog(
        navigationBarItemNumber = uiState.value.navigationBarItemClicked,
        viewModel = viewModel,
        myArtSpaceDao = myArtSpaceDao,
        uiState = uiState
    )
}

@Composable
fun BottomNavigationBar(
    viewModel: MyArtSpaceAppViewModel,
) {
    NavigationBar() {
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(1) },
            label = {
                Text(
                    text = stringResource(id = R.string.add_album),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = stringResource(id = R.string.add_album)
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(2) },
            label = {
                Text(
                    text = stringResource(R.string.search),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = stringResource(R.string.search)
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(3) },
            label = {
                Text(
                    text = stringResource(R.string.account),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.account)
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(4) },
            label = {
                Text(
                    text = stringResource(R.string.settings),
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Settings,
                    contentDescription = stringResource(R.string.settings)
                )
            }
        )
    }
}

@Composable
fun CreateDialog(
    navigationBarItemNumber: Int,
    viewModel: MyArtSpaceAppViewModel,
    uiState: State<MyArtSpaceUiState>,
    myArtSpaceDao: MyArtSpaceDao
) {
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, flag)
            viewModel.userInputNewAlbumImage = uri.toString()
            viewModel.updateUserInputNewAlbumImage(viewModel.userInputNewAlbumImage)
        }
    }


    when (navigationBarItemNumber) {
        1 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = null) },
            title = stringResource(id = R.string.add_album),
            dialogText = {
                AddAlbumDialogText(
                    albumTitle = viewModel.userInputNewAlbumTitle,
                    onUserAlbumTitleChanged = { viewModel.updateUserInputNewAlbumTitle(it) },
                    albumDescription = viewModel.userInputNewAlbumDescription,
                    onUserAlbumDescriptionChanged = {
                        viewModel.updateUserInputNewAlbumDescription(
                            it
                        )
                    },
                    albumCreationDate = viewModel.userInputNewAlbumCreationDate,
                    onUserAlbumCreationDateChanged = {
                        viewModel.updateUserInputNewAlbumCreationDate(
                            it
                        )
                    },
                    onAddImageButtonClicked = {
                        singlePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    uiState = uiState
                )
            },
            onConfirmButtonClicked = {
                val album = Album(
                    title = viewModel.userInputNewAlbumTitle,
                    description = viewModel.userInputNewAlbumDescription,
                    image = viewModel.userInputNewAlbumImage,
                    createDate = viewModel.userInputNewAlbumCreationDate,
                )
                thread {
                    myArtSpaceDao.createAlbum(album)
                }
                viewModel.navigateToHomeScreenFromDialog()
            },
            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

        2 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.Search, contentDescription = null) },
            title = stringResource(id = R.string.search),
            dialogText = { SearchDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

        3 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
            title = stringResource(id = R.string.account),
            dialogText = { AccountDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

        4 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            title = stringResource(id = R.string.settings),
            dialogText = { SettingsDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

        else -> {}
    }
}

@Composable
fun SettingsDialogText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

    }
}

@Composable
fun AccountDialogText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

    }
}

@Composable
fun SearchDialogText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Dialog.DialogTextField(
            value = "",
            labelText = stringResource(R.string.search),
            onValueChange = {}
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}

@Composable
fun AddAlbumDialogText(
    albumTitle: String,
    onUserAlbumTitleChanged: (String) -> Unit,
    albumDescription: String,
    onUserAlbumDescriptionChanged: (String) -> Unit,
    albumCreationDate: String,
    onUserAlbumCreationDateChanged: (String) -> Unit,
    onAddImageButtonClicked: () -> Unit,
    uiState: State<MyArtSpaceUiState>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Dialog.DialogTextField(
            value = albumTitle,
            labelText = stringResource(R.string.add_album_title),
            onValueChange = onUserAlbumTitleChanged
        )
        Dialog.DialogTextField(
            value = albumDescription,
            labelText = stringResource(R.string.add_album_description),
            onValueChange = onUserAlbumDescriptionChanged
        )
        Dialog.DialogTextField(
            value = albumCreationDate,
            labelText = stringResource(R.string.add_album_creation_date),
            onValueChange = onUserAlbumCreationDateChanged
        )
        Row {

            Button(
                onClick = onAddImageButtonClicked,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
                Icon(if (!uiState.value.isAlbumPhotoAdded) painterResource(R.drawable.add_photo) else
                    painterResource(R.drawable.icon_image),
                    contentDescription = "add photo"
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
                Text(text = "Add Album Image")
            }
        }

    }
}

@Composable
fun AlbumsLazyColumn(
    albumList: List<Album>,
) {

    val color = MaterialTheme.colorScheme.outlineVariant

    LazyColumn(modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))) {
        items(albumList) { album ->
            Card(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(120.dp)
                    .clickable { },
                elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
            ) {
                Row(
                    modifier = Modifier
                        .background(color = color)
                        .padding(dimensionResource(R.dimen.padding_small)),
                ) {
                    AsyncImage(
                        model = album.image,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            //.height(60.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier.weight(2f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = album.title,
                            fontWeight = FontWeight.SemiBold,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_extra_small))
                        )
                        Column(
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.padding_extra_small)),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = album.description,
                                style = MaterialTheme.typography.bodySmall,
                            )
                            Text(
                                text = album.createDate,
                                style = MaterialTheme.typography.bodySmall
                            )
//                            Text(
//                                text = album.artNumber.toString(),
//                                style = MaterialTheme.typography.bodySmall
//                            )
                        }
                    }
                    Column {
                        EditButton()
                        DeleteButton()
                    }
                }
            }
        }
    }
}

@Composable
fun EditButton() {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Sharp.Create,
            contentDescription = "Edit Album",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun DeleteButton() {
    IconButton(onClick = { }) {
        Icon(
            imageVector = Icons.Sharp.Delete,
            contentDescription = "Edit Album",
            modifier = Modifier.size(18.dp)
        )
    }
}



