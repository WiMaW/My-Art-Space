package com.wioletamwrobel.myartspace

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Home
import androidx.compose.material.icons.sharp.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.wioletamwrobel.myartspace.model.Art
import com.wioletamwrobel.myartspace.model.MyArtDao
import com.wioletamwrobel.myartspace.ui.theme.md_theme_light_primary
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ArtCardScreen(
    albumId: Long,
    viewModel: MyArtSpaceAppViewModel,
    uiState: State<MyArtSpaceUiState>,
    navController: NavController,
    myArtDao: MyArtDao,
    artListSizeFromCurrentAlbum: Int,
) {
    when (viewModel.artListInCurrentAlbum.size) {
        0 -> {
            ArtCardScreenAppWithoutArts(
                viewModel = viewModel,
            )
        }
        else -> {
            ArtCardScreenWithArts(
                viewModel = viewModel,
                artListSizeFromCurrentAlbum = artListSizeFromCurrentAlbum,
                onClickHomeButton = { navController.navigate("home_screen") },
                uiState = uiState,
                myArtDao = myArtDao,
                navController = navController
            )
        }
    }
    if (uiState.value.isAddArtButtonClicked) {
        AddArtAlertDialog(
            uiState = uiState,
            viewModel = viewModel,
            myArtDao = myArtDao,
            albumId = albumId,
            navController = navController
        )
    }
}

@Composable
fun ArtCardScreenAppWithoutArts(
    viewModel: MyArtSpaceAppViewModel,
) {
    Column(
        modifier = Modifier
            .padding(vertical = dimensionResource(R.dimen.padding_small))
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AppNameAndIcon()
        Row(modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))) {
            Button(
                onClick = { viewModel.navigateToAddArtAlertDialog() },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    painterResource(R.drawable.add_photo),
                    contentDescription = "add photo"
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
                Text(text = stringResource(R.string.add_art_title))
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtCardScreenWithArts(
    viewModel: MyArtSpaceAppViewModel,
    uiState: State<MyArtSpaceUiState>,
    artListSizeFromCurrentAlbum: Int,
    onClickHomeButton: () -> Unit,
    myArtDao: MyArtDao,
    navController: NavController
) {

    Scaffold(
        floatingActionButton = {
            AddArtFloatingActionButton(viewModel = viewModel)
        },
        modifier = Modifier
            .padding(
                vertical = dimensionResource(id = R.dimen.padding_large),
                horizontal = dimensionResource(
                    id = R.dimen.padding_small
                )
            )
            .fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(
                    rememberScrollState()
                )
        ) {
            ArtAndDescriptionCard(
                clickLimit = artListSizeFromCurrentAlbum,
                artList = viewModel.artListInCurrentAlbum.toMutableStateList(),
                onClickHomeButton = onClickHomeButton,
                viewModel = viewModel,
                uiState = uiState,
                myArtDao = myArtDao,
                navController = navController
            )
        }
    }
}

@Composable
fun AddArtFloatingActionButton(
    viewModel: MyArtSpaceAppViewModel
) {
    FloatingActionButton(
        onClick = { viewModel.navigateToAddArtAlertDialog() },
        shape = MaterialTheme.shapes.small,
        containerColor = md_theme_light_primary,
        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
    ) {
        Icon(
            painterResource(R.drawable.add_photo),
            contentDescription = "add photo"
        )
    }
}

@Composable
fun AddArtAlertDialog(
    uiState: State<MyArtSpaceUiState>,
    navController: NavController,
    viewModel: MyArtSpaceAppViewModel,
    albumId: Long,
    myArtDao: MyArtDao
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        val flag = Intent.FLAG_GRANT_READ_URI_PERMISSION
        if (uri != null) {
            context.contentResolver.takePersistableUriPermission(uri, flag)
            viewModel.userInputNewArtImage = uri.toString()
            viewModel.updateUserInputNewArtImage(viewModel.userInputNewArtImage)
        }
    }
    Dialog.CreateDialog(
        icon = {
            Icon(
                painterResource(R.drawable.add_photo),
                contentDescription = "add photo"
            )
        },
        title = stringResource(R.string.add_art_title),
        dialogText = {
            AddArtDialogText(
                artTitle = viewModel.userInputNewArtTitle,
                onUserArtTitleChanged = { viewModel.updateUserInputNewArtTitle(it) },
                artMethod = viewModel.userInputNewArtMethod,
                onUserArtMethodChanged = { viewModel.updateUserInputNewArtMethod(it) },
                artDate = viewModel.userInputNewArtDate,
                onUserArtDateChanged = { viewModel.updateUserInputNewArtDate(it) },
                onAddImageButtonClicked = {
                    singlePhotoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                uiState = uiState
            )
        },
        onDismissButtonClicked = {
            navController.navigate("art_card_screen")
            viewModel.navigateToArtCardScreenFromAlertDialog()
            viewModel.clearUserInputNewArtFields()
        },
        onConfirmButtonClicked = {
            val art = Art(
                title = viewModel.userInputNewArtTitle,
                method = viewModel.userInputNewArtMethod,
                date = viewModel.userInputNewArtDate,
                image = viewModel.userInputNewArtImage,
                albumId = albumId,
            )
            Dispatchers.IO.dispatch(scope.coroutineContext) {
                myArtDao.createArt(art)
                viewModel.updateArtAmountInCurrentAlbum(
                    myArtDao.getAllArtsFromCurrentAlbum(
                        albumId
                    ).size
                )
                viewModel.updateCurrentAlbumArtListToDisplay(
                    myArtDao.getAllArtsFromCurrentAlbum(
                        albumId
                    )
                )
            }
            navController.navigate("art_card_screen")
            viewModel.navigateToArtCardScreenFromAlertDialog()
            viewModel.clearUserInputNewArtFields()
        },
    )
}

@Composable
fun AddArtDialogText(
    artTitle: String,
    onUserArtTitleChanged: (String) -> Unit,
    artMethod: String,
    onUserArtMethodChanged: (String) -> Unit,
    artDate: String,
    onUserArtDateChanged: (String) -> Unit,
    onAddImageButtonClicked: () -> Unit,
    uiState: State<MyArtSpaceUiState>
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Dialog.DialogTextField(
            value = artTitle,
            labelText = stringResource(R.string.add_album_title),
            onValueChange = onUserArtTitleChanged
        )
        Dialog.DialogTextField(
            value = artMethod,
            labelText = stringResource(R.string.add_art_method),
            onValueChange = onUserArtMethodChanged
        )
        Dialog.DialogTextField(
            value = artDate,
            labelText = stringResource(R.string.add_album_date),
            onValueChange = onUserArtDateChanged
        )
        Row {

            Button(
                onClick = onAddImageButtonClicked,
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                Icon(
                    if (!uiState.value.isArtPhotoAdded) painterResource(R.drawable.add_photo) else
                        painterResource(R.drawable.icon_image),
                    contentDescription = "add photo"
                )
                Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
                Text(text = if (!uiState.value.isArtPhotoAdded) "Add Art Image" else "Image added")
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNameAndIcon(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(modifier = modifier) {
                Image(
                    painter = painterResource(R.drawable.app_icon2),
                    contentDescription = null,
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                        .clip(MaterialTheme.shapes.small),
                )
                Text(
                    text = stringResource(R.string.app_name),
                    modifier = Modifier.padding(
                        top = dimensionResource(R.dimen.padding_medium)
                    ),
                    style = MaterialTheme.typography.displayLarge
                )
            }
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ArtAndDescriptionCard(
    modifier: Modifier = Modifier,
    artList: MutableList<Art>,
    clickLimit: Int,
    onClickHomeButton: () -> Unit,
    viewModel: MyArtSpaceAppViewModel,
    uiState: State<MyArtSpaceUiState>,
    myArtDao: MyArtDao,
    navController: NavController
) {

    val pagerState = rememberPagerState {
        clickLimit
    }

    val scope = rememberCoroutineScope()

    viewModel.updateArtId(artList[pagerState.currentPage].artId)

    Card(
        modifier = modifier
            .padding(horizontal = dimensionResource(R.dimen.padding_small))
            .fillMaxSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row {
                HomeButton(
                    onClickHomeButton = onClickHomeButton
                )
                Spacer(modifier = Modifier.weight(1f))
                Column {
                    MenuButton(viewModel = viewModel)
                    if (uiState.value.isDropDownMenuVisible) {
                        DropDownMenu(
                            viewModel = viewModel,
                            myArtDao = myArtDao,
                            navController = navController
                        )
                    }
                }
            }
        }
        HorizontalPager(
            modifier = Modifier,
            state = pagerState,
            pageSpacing = 0.dp,
            userScrollEnabled = true,
            reverseLayout = false,
            contentPadding = PaddingValues(0.dp),
            beyondBoundsPageCount = 0,
            pageSize = PageSize.Fill,
            flingBehavior = PagerDefaults.flingBehavior(state = pagerState),
            key = null,
            pageNestedScrollConnection = PagerDefaults.pageNestedScrollConnection(
                Orientation.Horizontal
            ),
            pageContent = { page ->
                Column(
                    modifier = modifier,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    AsyncImage(
                        model = artList[page].image,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(dimensionResource(R.dimen.padding_small))
                            .fillMaxWidth()
                            .height(380.dp),
                        contentScale = ContentScale.Crop
                    )
                    Text(
                        modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_small)),
                        text = artList[page].title,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = artList[page].method,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        modifier = Modifier
                            .padding(
                                bottom = dimensionResource(R.dimen.padding_medium),
                                top = dimensionResource(R.dimen.padding_extra_small)
                            ),
                        text = artList[page].date,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

            })
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        viewModel.updateArtId(artList[pagerState.currentPage].artId)
                    }
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_navigate_before),
                    contentDescription = ""
                )
            }
            IconButton(
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        viewModel.updateArtId(artList[pagerState.currentPage].artId)
                    }
                },
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_navigate_next),
                    contentDescription = ""
                )
            }
        }
    }
}

@Composable
fun MenuButton(
    viewModel: MyArtSpaceAppViewModel
) {
    IconButton(onClick = { viewModel.openDropDownMenu() }) {
        Icon(
            imageVector = Icons.Sharp.Menu,
            contentDescription = "Art Menu",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun HomeButton(onClickHomeButton: () -> Unit) {
    IconButton(onClick = onClickHomeButton) {
        Icon(
            imageVector = Icons.Sharp.Home,
            contentDescription = "Return to home",
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
fun DropDownMenu(
    viewModel: MyArtSpaceAppViewModel,
    myArtDao: MyArtDao,
    navController: NavController
) {

    val scope = rememberCoroutineScope()

    DropdownMenu(expanded = true, onDismissRequest = { viewModel.closeDropDownMenu() }) {
        DropdownMenuItem(
            text = { Text(text = "Delete Art") },
            onClick = {
                Dispatchers.IO.dispatch(scope.coroutineContext) {
                    myArtDao.deleteArt(viewModel.currentArtId)
                    viewModel.updateCurrentAlbumArtListToDisplay(
                        myArtDao.getAllArtsFromCurrentAlbum(
                            viewModel.currentAlbumId
                        )
                    )
                    viewModel.updateArtAmountInCurrentAlbum(
                        myArtDao.getAllArtsFromCurrentAlbum(
                            viewModel.currentAlbumId
                        ).size
                    )
                }
                navController.navigate("art_card_screen")
                viewModel.closeDropDownMenu()
            })
        DropdownMenuItem(text = { Text(text = "Edit Art") }, onClick = { /*TODO*/ })
        DropdownMenuItem(text = { Text(text = "Share") }, onClick = { /*TODO*/ })
    }
}