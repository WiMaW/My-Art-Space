package com.wioletamwrobel.myartspace

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.twotone.List
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.wioletamwrobel.myartspace.model.Art
import com.wioletamwrobel.myartspace.ui.theme.Shapes

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArtCardScreenApp(
    albumId: Long,
    viewModel: MyArtSpaceAppViewModel,
    //artList: List<Art>,
) {
    var click by remember {
        mutableStateOf(0)
    }

    var artList: List<Art> by remember {
        mutableStateOf(emptyList())
    }

    val artListSize: Int by lazy { artList.size }
    val clickLimit: Int by lazy { artListSize - 1 }


    Scaffold() {
        Column(
            modifier = Modifier
                .padding(vertical = dimensionResource(R.dimen.padding_medium))
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppNameAndIcon()
            if (artListSize > 0) {
                ActionButton(
                    onClick = { if (click > 0) click-- else click = clickLimit },
                    text = "PREV",
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_medium),
                            bottom = dimensionResource(R.dimen.padding_medium)
                        )
                )
                ArtAndDescriptionCard(click = click, artList = artList)
                Row(
                    modifier = Modifier,
                ) {
                    ChangeViewButton(onClick = {})
                }
                ActionButton(
                    onClick = { if (click < clickLimit) click++ else click = 0 },
                    text = "NEXT",
                    Modifier.padding(
                        dimensionResource(R.dimen.padding_extra_small)
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                BottomNavigationBar(
                    viewModel = viewModel,
                    textItemOne = stringResource(id = R.string.edit_art),
                    iconItemOne = Icons.Filled.Edit,
                    contentDescriptionItemOne = stringResource(id = R.string.edit_art),
                    textItemTwo = stringResource(R.string.delete_art),
                    iconItemTwo = Icons.Filled.Delete,
                    contentDescriptionItemTwo = stringResource(R.string.delete_art),
                    textItemThree = stringResource(R.string.share_art),
                    iconItemThree = Icons.Filled.Share,
                    contentDescriptionItemThree = stringResource(R.string.share_art),
                    textItemFour = stringResource(R.string.add_art),
                    iconItemFour = Icons.Filled.Add,
                    contentDescriptionItemFour = stringResource(R.string.add_art),
                )
            }
            else {
                Row (modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_medium))) {
                    Button(
                        onClick = {  },
                        shape = MaterialTheme.shapes.small,
                        modifier = Modifier.padding(dimensionResource(id = R.dimen.padding_small))) {
                        Icon(painterResource(R.drawable.add_photo),
                            contentDescription = "add photo"
                        )
                        Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.padding_extra_small)))
                        Text(text = "Add your first piece of art")
                    }
                }
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

@Composable
fun ActionButton(
    onClick: () -> Unit,
    text: String,
    modifier: Modifier,
) {
    Button(
        onClick = onClick,
        modifier = modifier.widthIn(min = 250.dp),
        shape = Shapes.small
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ArtAndDescriptionCard(
    modifier: Modifier = Modifier,
    artList: List<Art>,
    click: Int
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
    ) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Row {
                Spacer(modifier = modifier.weight(1f))
                EditButton()
            }
            AsyncImage(
                model = artList[click].image,
                contentDescription = null,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
                    text = artList[click].title,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = artList[click].method,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    modifier = Modifier
                        .padding(
                            bottom = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_extra_small)
                        ),
                    text = artList[click].date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
fun ChangeViewButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.TwoTone.List,
            contentDescription = stringResource(id = R.string.change_view_button),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun MyArtSpacePreview() {
//    MyArtSpaceTheme {
//        ArtCardScreenApp(artList = Datasource().LoadArt())
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun MyArtSpaceDarkPreview() {
//    MyArtSpaceTheme(darkTheme = true) {
//        ArtCardScreenApp(artList = Datasource().LoadArt())
//    }
//}
