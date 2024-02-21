package com.wioletamwrobel.myartspace

import android.annotation.SuppressLint
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.sharp.Delete
import androidx.compose.material.icons.sharp.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wioletamwrobel.myartspace.model.Album
import com.wioletamwrobel.myartspace.model.MyArtDao
import com.wioletamwrobel.myartspace.model.MyArtSpaceDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import kotlin.math.absoluteValue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    uiState: State<MyArtSpaceUiState>,
    viewModel: MyArtSpaceAppViewModel,
    myArtSpaceDao: MyArtSpaceDao,
    myArtDao: MyArtDao,
    albumList: List<Album>,
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = { AppNameAndIcon() },
        bottomBar = {
            BottomNavigationBar(
                viewModel = viewModel,
                textItemOne = stringResource(id = R.string.add_album),
                iconItemOne = Icons.Filled.Add,
                contentDescriptionItemOne = stringResource(id = R.string.add_album),
                textItemTwo = stringResource(R.string.search),
                iconItemTwo = Icons.Filled.Search,
                contentDescriptionItemTwo = stringResource(R.string.search),
                textItemThree = stringResource(R.string.account),
                iconItemThree = Icons.Filled.AccountCircle,
                contentDescriptionItemThree = stringResource(R.string.account),
//                textItemFour = stringResource(R.string.settings),
//                iconItemFour = Icons.Filled.Settings,
//                contentDescriptionItemFour = stringResource(R.string.settings),
            )
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        modifier = Modifier
            .padding(top = dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            if (viewModel.albumListToDisplay.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    ImageSlider()
                }
            } else {
                AlbumsLazyColumn(
                    albumList = albumList,
                    navController = navController,
                    viewModel = viewModel,
                    myArtDao = myArtDao,
                    myArtSpaceDao = myArtSpaceDao,
                    uiState = uiState,
                    scope = scope,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
    CreateDialogsForNavigationBarItems(
        navigationBarItemNumber = uiState.value.navigationBarItemClicked,
        viewModel = viewModel,
        myArtSpaceDao = myArtSpaceDao,
        uiState = uiState,
        myArtDao = myArtDao,
        navController = navController,
        scope = scope
    )
}

@Composable
fun BottomNavigationBar(
    viewModel: MyArtSpaceAppViewModel,
    textItemOne: String,
    iconItemOne: ImageVector,
    contentDescriptionItemOne: String,
    textItemTwo: String,
    iconItemTwo: ImageVector,
    contentDescriptionItemTwo: String,
    textItemThree: String,
    iconItemThree: ImageVector,
    contentDescriptionItemThree: String,
//    textItemFour: String,
//    iconItemFour: ImageVector,
//    contentDescriptionItemFour: String,

) {
    NavigationBar {
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(1) },
            label = {
                Text(
                    text = textItemOne,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = iconItemOne,
                    contentDescription = contentDescriptionItemOne
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(2) },
            label = {
                Text(
                    text = textItemTwo,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = iconItemTwo,
                    contentDescription = contentDescriptionItemTwo
                )
            }
        )
        NavigationBarItem(
            selected = true,
            onClick = { viewModel.navigateToBarItemDialog(3) },
            label = {
                Text(
                    text = textItemThree,
                    style = MaterialTheme.typography.labelSmall
                )
            },
            icon = {
                Icon(
                    imageVector = iconItemThree,
                    contentDescription = contentDescriptionItemThree
                )
            }
        )
//        NavigationBarItem(
//            selected = true,
//            onClick = { viewModel.navigateToBarItemDialog(4) },
//            label = {
//                Text(
//                    text = textItemFour,
//                    style = MaterialTheme.typography.labelSmall
//                )
//            },
//            icon = {
//                Icon(
//                    imageVector = iconItemFour,
//                    contentDescription = contentDescriptionItemFour
//                )
//            }
//        )
    }
}

@Composable
fun CreateDialogsForNavigationBarItems(
    navigationBarItemNumber: Int,
    viewModel: MyArtSpaceAppViewModel,
    uiState: State<MyArtSpaceUiState>,
    myArtSpaceDao: MyArtSpaceDao,
    myArtDao: MyArtDao,
    navController: NavController,
    scope: CoroutineScope
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
            title = if (uiState.value.isEditAlbumButtonClicked)
                stringResource(id = R.string.edit_album) else stringResource(id = R.string.add_album),
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
                val album =
                    if (uiState.value.isEditAlbumButtonClicked) {
                        Album(
                            id = viewModel.currentAlbumId,
                            title = viewModel.userInputNewAlbumTitle.ifEmpty {
                                viewModel.currentAlbumDetails.title
                            },
                            description = viewModel.userInputNewAlbumDescription.ifEmpty {
                                viewModel.currentAlbumDetails.description
                            },
                            image = viewModel.userInputNewAlbumImage.ifEmpty {
                                viewModel.currentAlbumDetails.image
                            },
                            createDate = viewModel.userInputNewAlbumCreationDate.ifEmpty {
                                viewModel.currentAlbumDetails.createDate
                            },
                        )
                    } else {
                        Album(
                            title = viewModel.userInputNewAlbumTitle,
                            description = viewModel.userInputNewAlbumDescription,
                            image = viewModel.userInputNewAlbumImage,
                            createDate = viewModel.userInputNewAlbumCreationDate,
                        )
                    }
                if (uiState.value.isEditAlbumButtonClicked) {
                    Dispatchers.IO.dispatch(scope.coroutineContext) {
                        myArtSpaceDao.updateAlbum(album)
                        viewModel.updateAlbumListToDisplay(myArtSpaceDao.getAllAlbums())
                    }
                    viewModel.navigateToHomeScreenFromEditAlbumAlertDialog()
                } else {
                    Dispatchers.IO.dispatch(scope.coroutineContext) {
                        myArtSpaceDao.createAlbum(album)
                        viewModel.updateAlbumListToDisplay(myArtSpaceDao.getAllAlbums())
                    }
                }
                viewModel.navigateToHomeScreenFromDialog()
                viewModel.clearUserInputNewAlbumFields()
            },
            onDismissButtonClicked = {
                viewModel.navigateToHomeScreenFromDialog()
                viewModel.clearUserInputNewAlbumFields()
            })

        2 -> Dialog(onDismissRequest = { viewModel.navigateToHomeScreenFromDialog() }) {
            SearchDialogText(
                viewModel = viewModel,
                myArtDao = myArtDao,
                navController = navController,
                scope = scope
            )
        }

        3 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
            title = stringResource(id = R.string.account),
            dialogText = { AccountDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

//        4 -> Dialog.CreateDialog(
//            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
//            title = stringResource(id = R.string.settings),
//            dialogText = { SettingsDialogText() },
//            onConfirmButtonClicked = { /*TODO*/ },
//            onDismissButtonClicked = { viewModel.navigateToHomeScreenFromDialog() })

        else -> {}
    }
}

//@Composable
//fun SettingsDialogText() {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center,
//    ) {
//
//    }
//}

@Composable
fun AccountDialogText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

    }
}

@Composable
fun SearchDialogText(
    viewModel: MyArtSpaceAppViewModel,
    myArtDao: MyArtDao,
    navController: NavController,
    scope: CoroutineScope
) {
    Card(
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .height(400.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
    ) {
        Column(modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_medium))) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Dialog.DialogTextField(
                    value = viewModel.searchText,
                    labelText = stringResource(R.string.search),
                    onValueChange = viewModel::onSearchTextChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )
            }
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(viewModel.albumListToDisplay) { album ->
                    if (album.doesMatchSearchQuery(viewModel.searchText.toString())) {
                        Row(
                            modifier = Modifier
                                .padding(vertical = dimensionResource(id = R.dimen.padding_small))
                                .clickable {
                                    viewModel.navigateToHomeScreenFromDialog()
                                    navController.navigate("art_card_screen")
                                    viewModel.updateAlbumId(album.id)
                                    Dispatchers.IO.dispatch(scope.coroutineContext) {
                                        viewModel.updateArtAmountInCurrentAlbum(
                                            myArtDao.getAllArtsFromCurrentAlbum(
                                                viewModel.currentAlbumId
                                            ).size
                                        )
                                        viewModel.updateCurrentAlbumArtListToDisplay(
                                            myArtDao.getAllArtsFromCurrentAlbum(
                                                viewModel.currentAlbumId
                                            )
                                        )
                                    }
                                    viewModel.clearUserInputSearchField()
                                }
                        ) {
                            Text(text = album.title)
                            Spacer(modifier = Modifier.weight(1f))
                            Text(text = album.createDate)
                        }
                        Divider(modifier = Modifier.height(1.dp))
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
            onValueChange = onUserAlbumTitleChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
        Dialog.DialogTextField(
            value = albumDescription,
            labelText = stringResource(R.string.add_album_description),
            onValueChange = onUserAlbumDescriptionChanged,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
            Dialog.DialogTextField(
                value = albumCreationDate,
                labelText = stringResource(R.string.add_album_date),
                onValueChange = onUserAlbumCreationDateChanged,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            )
        Row {

            Button(
                onClick = onAddImageButtonClicked,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    if (!uiState.value.isAlbumPhotoAdded) painterResource(R.drawable.add_photo) else
                        painterResource(R.drawable.icon_image),
                    contentDescription = "add photo"
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
                Text(text = if (!uiState.value.isAlbumPhotoAdded) "Add Album Image" else "Image added")
            }
        }
    }
}

@Composable
fun AlbumsLazyColumn(
    albumList: List<Album>,
    navController: NavController,
    viewModel: MyArtSpaceAppViewModel,
    myArtDao: MyArtDao,
    myArtSpaceDao: MyArtSpaceDao,
    uiState: State<MyArtSpaceUiState>,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {

    val color = MaterialTheme.colorScheme.outlineVariant

    LazyColumn(
        modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
    ) {
        items(albumList) { album ->
            Card(
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .height(120.dp)
                    .clickable {
                        navController.navigate("art_card_screen")
                        viewModel.updateAlbumId(album.id)
                        Dispatchers.IO.dispatch(scope.coroutineContext) {
                            viewModel.updateArtAmountInCurrentAlbum(
                                myArtDao.getAllArtsFromCurrentAlbum(
                                    viewModel.currentAlbumId
                                ).size
                            )
                            viewModel.updateCurrentAlbumArtListToDisplay(
                                myArtDao.getAllArtsFromCurrentAlbum(
                                    viewModel.currentAlbumId
                                )
                            )
                        }
                    },
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
                        EditButton(
                            onEditButtonClicked = {
                                viewModel.navigateToEditAlbumAlertDialog()
                                viewModel.navigateToBarItemDialog(1)
                                viewModel.updateAlbumId(album.id)
                                Dispatchers.IO.dispatch(scope.coroutineContext) {
                                    viewModel.updateCurrentAlbumDetails(
                                        myArtSpaceDao.getAlbumById(
                                            viewModel.currentAlbumId
                                        )
                                    )
                                }
                            }
                        )
                        DeleteButton(
                            onDeleteButtonClicked = {
                                viewModel.openSnackbarBeforeDeletingAlbum()
                                viewModel.updateAlbumId(album.id)
                            })
                    }
                    if (uiState.value.isDeleteAlbumIconButtonClicked) {
                        SnackbarBeforeDeletingAlbum(
                            scope = scope,
                            snackbarHostState = snackbarHostState,
                            viewModel = viewModel,
                            navController = navController,
                            myArtSpaceDao = myArtSpaceDao,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EditButton(onEditButtonClicked: () -> Unit) {
    IconButton(onClick = onEditButtonClicked) {
        Icon(
            imageVector = Icons.Sharp.Edit,
            contentDescription = "Edit Album",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun DeleteButton(onDeleteButtonClicked: () -> Unit) {
    IconButton(onClick = onDeleteButtonClicked) {
        Icon(
            imageVector = Icons.Sharp.Delete,
            contentDescription = "Edit Album",
            modifier = Modifier.size(18.dp)
        )
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun SnackbarBeforeDeletingAlbum(
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    viewModel: MyArtSpaceAppViewModel,
    navController: NavController,
    myArtSpaceDao: MyArtSpaceDao,
) {
    scope.launch {
        val result = snackbarHostState
            .showSnackbar(
                message = "Are you sure you want to delete this album?",
                actionLabel = "YES",
                withDismissAction = true,
                duration = SnackbarDuration.Indefinite
            )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                Dispatchers.IO.dispatch(scope.coroutineContext) {
                    myArtSpaceDao.deleteAlbum(viewModel.currentAlbumId)
                    myArtSpaceDao.deleteAllArtFromAlbum(viewModel.currentAlbumId)
                    viewModel.updateAlbumListToDisplay(myArtSpaceDao.getAllAlbums())
                }
                viewModel.closeSnackbarBeforeDeletingAlbum()
                navController.navigate("home_screen")
            }

            SnackbarResult.Dismissed -> {
                viewModel.closeSnackbarBeforeDeletingAlbum()
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ImageSlider() {
    val pagerState = rememberPagerState { 4 }
    val imageSlider = listOf(
        painterResource(id = R.drawable.alice_donovan_rouse_yu68fuqdvoi_unsplash),
        painterResource(id = R.drawable.dan_farrell_ft49qnfucq8_unsplash),
        painterResource(id = R.drawable.rhondak_native_florida_folk_artist__yc7otffn_0_unsplash),
        painterResource(id = R.drawable.steve_johnson_3sf_g9m0gcq_unsplash)
    )

    LaunchedEffect(Unit) {
        while (true) {
            yield()
            delay(3000)
            pagerState.animateScrollToPage(
                page = (pagerState.currentPage + 1) % (pagerState.pageCount)
            )
        }
    }

    HorizontalPager(
        state = pagerState,
        beyondBoundsPageCount = 1,
        pageSpacing = 10.dp,
        modifier = Modifier.padding(
            horizontal = dimensionResource(
                id = R.dimen.padding_medium
            )
        )
    ) { page ->
        Card(
            Modifier
                .fillMaxWidth()
                .graphicsLayer {
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Image(
                painter = imageSlider[page],
                contentDescription = "image slider",
                contentScale = ContentScale.Crop,
                modifier = Modifier.height(350.dp)
            )
        }
    }
}



