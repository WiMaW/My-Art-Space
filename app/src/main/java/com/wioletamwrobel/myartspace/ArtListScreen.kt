package com.wioletamwrobel.myartspace.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.KeyboardArrowDown
import androidx.compose.material.icons.twotone.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wioletamwrobel.myartspace.AppNameAndIcon
import com.wioletamwrobel.myartspace.BottomNavigationBar
import com.wioletamwrobel.myartspace.R
import com.wioletamwrobel.myartspace.model.Art
import com.wioletamwrobel.myartspace.ui.theme.MyArtSpaceTheme


//@Composable
//fun ArtListScreen(modifier: Modifier = Modifier) {
//    Column (modifier = modifier
//                .padding(dimensionResource(R.dimen.padding_medium)),
//            ){
//                AppNameAndIcon()
//                Spacer(modifier = modifier
//                    .padding(dimensionResource(R.dimen.padding_extra_small)))
//                Row (){
//                    ChangeViewButton(
//                        onClick = {})
//                }
//                LoadArtList(artList = Datasource().LoadArt())
//        BottomNavigationBar()
//    }
//}
//@Composable
//fun LoadArtList(
//    artList: List<Art>,
//    modifier: Modifier = Modifier,
//) {
//    LazyColumn(modifier = modifier) {
//        items(artList) { art ->
//            ArtList(
//                art = art
//            )
//        }
//    }
//}
//
//@Composable
//fun ArtList(
//    art: Art,
//    modifier: Modifier = Modifier
//){
//    var expanded by remember { mutableStateOf(false) }
//    val color by animateColorAsState(
//        targetValue =
//        if (expanded) MaterialTheme.colorScheme.errorContainer
//        else MaterialTheme.colorScheme.outlineVariant
//    )
//    Card(
//        modifier = modifier
//            .padding(dimensionResource(R.dimen.padding_extra_small)),
//        elevation = CardDefaults.cardElevation(defaultElevation = dimensionResource(R.dimen.padding_extra_small))
//    ){
//        Row (
//            modifier = modifier
//                .background(color = color)
//                .padding(dimensionResource(R.dimen.padding_small)),
//        ) {
//            Image(
//                painter = painterResource(art.image),
//                contentDescription = null,
//                modifier = modifier
//                    .height(60.dp)
//                    .weight(1f)
//            )
//            Column (
//                modifier = modifier.weight(2f),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ){
//                Text(
//                    text = stringResource(art.title),
//                    fontWeight = FontWeight.SemiBold,
//                    style = MaterialTheme.typography.bodyMedium,
//                    modifier = modifier.padding(top = dimensionResource(id = R.dimen.padding_extra_small))
//                )
//                if(expanded) {
//                    Column (modifier = modifier
//                        .padding(dimensionResource(R.dimen.padding_extra_small)),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ){
//                        Text(
//                            text = stringResource(art.method),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        Text(
//                            text = stringResource(art.date),
//                            style = MaterialTheme.typography.bodySmall
//                        )
//                        EditButton()
//                    }
//                }
//            }
//            ExpandButton(expanded = expanded, onClick = { expanded = !expanded })
//        }
//    }
//}
//@Composable
//fun ExpandButton(
//    expanded: Boolean,
//    onClick: () -> Unit,
//    modifier: Modifier = Modifier
//){
//    IconButton(
//        onClick = onClick,
//        modifier = modifier)
//    {
//        Icon(
//            imageVector = if (expanded) Icons.TwoTone.KeyboardArrowUp else Icons.TwoTone.KeyboardArrowDown,
//            contentDescription = stringResource(id = R.string.expand_button),
//            tint = MaterialTheme.colorScheme.secondary,)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ListScreenPreview() {
//    MyArtSpaceTheme {
//        ArtListScreen()
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ListScreenDarkPreview() {
//    MyArtSpaceTheme(darkTheme = true) {
//        ArtListScreen()
//    }
//}