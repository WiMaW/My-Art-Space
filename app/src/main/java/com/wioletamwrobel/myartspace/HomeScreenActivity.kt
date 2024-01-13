package com.wioletamwrobel.myartspace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource


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
    viewModel: MyArtSpaceAppViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        modifier = Modifier
            .padding(dimensionResource(id = R.dimen.padding_medium))
            .fillMaxWidth()
    ) {
        AppNameAndIcon()
//        AlbumsLazyColumn(
//            albumList = Datasource().LoadAlbum(),
//            modifier = Modifier
//                .padding(top = dimensionResource(R.dimen.padding_large))
//        )
        Spacer(modifier = Modifier.weight(1f))
        BottomNavigationBar(
            viewModel = viewModel
        )
    }
    CreateDialog(
        navigationBarItemNumber = uiState.value.navigationBarItemClicked,
        viewModel = viewModel)
}

@Composable
fun BottomNavigationBar(
    viewModel: MyArtSpaceAppViewModel,
    modifier: Modifier = Modifier
) {
    NavigationBar(modifier = modifier) {
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
            onClick = { viewModel.navigateToBarItemDialog(2)},
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
    viewModel: MyArtSpaceAppViewModel
) {
    when (navigationBarItemNumber) {
        1 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.AddCircle, contentDescription = null)},
            title = stringResource(id = R.string.add_album),
            dialogText = { AddAlbumDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = {viewModel.navigateToHomeScreenFromDialog()})
        2 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.Search, contentDescription = null)},
            title = stringResource(id = R.string.search),
            dialogText = { SearchDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = {viewModel.navigateToHomeScreenFromDialog()})
        3 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null)},
            title = stringResource(id = R.string.account),
            dialogText = { AccountDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = {viewModel.navigateToHomeScreenFromDialog()})
        4 -> Dialog.CreateDialog(
            icon = { Icon(Icons.Filled.Settings, contentDescription = null) },
            title = stringResource(id = R.string.settings),
            dialogText = { SettingsDialogText() },
            onConfirmButtonClicked = { /*TODO*/ },
            onDismissButtonClicked = {viewModel.navigateToHomeScreenFromDialog()})
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
            value = stringResource(R.string.search),
            onValueChange = {}
        )
        TextButton(onClick = { /*TODO*/ }) {
            Text(text = stringResource(id = R.string.search))
        }
    }
}

@Composable
fun AddAlbumDialogText() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Dialog.DialogTextField(
            value = stringResource(R.string.add_album_title),
            onValueChange = {}
        )
        Dialog.DialogTextField(
            value = stringResource(R.string.add_album_description),
            onValueChange = {}
        )
        Dialog.DialogTextField(
            value = stringResource(R.string.add_album_creation_date),
            onValueChange = {}
        )
        Dialog.DialogTextField(
            value = stringResource(R.string.add_album_image),
            onValueChange = {}
        )
    }
}

//@Composable
//fun AlbumsLazyColumn(
//    albumList: List<Album>,
//    modifier: Modifier = Modifier,
//) {
//    LazyColumn(modifier = modifier) {
//        items(albumList) { album ->
//            AlbumList(
//                album = album
//            )
//        }
//    }
//}
//
//@Composable
//fun AlbumList(
//    album: Album,
//    modifier: Modifier = Modifier
//) {
//    val color = MaterialTheme.colorScheme.outlineVariant
//    Card(
//        modifier = Modifier
//            .padding(dimensionResource(R.dimen.padding_extra_small))
//            .clickable { },
//        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
//    ) {
//        Row(
//            modifier = modifier
//                .background(color = color)
//                .padding(dimensionResource(R.dimen.padding_small)),
//        ) {
//            Image(
//                painter = painterResource(album.image),
//                contentDescription = null,
//                modifier = modifier
//                    .height(60.dp)
//                    .weight(1f)
//                    .align(Alignment.CenterVertically)
//            )
//            Column(
//                modifier = modifier.weight(2f),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Text(
//                    text = stringResource(album.title),
//                    fontWeight = FontWeight.SemiBold,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_extra_small))
//                )
//                Column(
//                    modifier = modifier
//                        .padding(dimensionResource(R.dimen.padding_extra_small)),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Row() {
//                        Text(
//                            text = stringResource(album.description),
//                            style = MaterialTheme.typography.bodySmall,
//                        )
//                        Text(
//                            text = " | ",
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        Text(
//                            text = stringResource(album.createDate),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                    }
//                    Text(
//                        text = album.artNumber.toString(),
//                        style = MaterialTheme.typography.bodySmall
//                    )
//                }
//            }
//            EditButton()
//        }
//    }
//}
//
//@Composable
//fun EditButton() {
//    IconButton(onClick = { }) {
//        Icon(
//            imageVector = Icons.Sharp.Create,
//            contentDescription = "Edit Album",
//            modifier = Modifier.size(18.dp)
//
//        )
//    }



