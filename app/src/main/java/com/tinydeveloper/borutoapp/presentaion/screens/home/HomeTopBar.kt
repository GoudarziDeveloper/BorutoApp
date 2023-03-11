package com.tinydeveloper.borutoapp.presentaion.screens.home

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.ui.theme.topAppBarBackgroundColor
import com.tinydeveloper.borutoapp.ui.theme.topAppBarContentColor

@Composable
fun HomeTopBar(onSearchClicked: () -> Unit){
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.search),
                color = MaterialTheme.colors.topAppBarContentColor
            )
        },
        backgroundColor = MaterialTheme.colors.topAppBarBackgroundColor,
        actions = {
            IconButton(onClick = { onSearchClicked() }){
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(R.string.search_icon),
                    tint = MaterialTheme.colors.topAppBarContentColor
                )
            }
        }
    )
}

@Preview
@Composable
private fun HomeTopBarPreview(){
    HomeTopBar {}
}