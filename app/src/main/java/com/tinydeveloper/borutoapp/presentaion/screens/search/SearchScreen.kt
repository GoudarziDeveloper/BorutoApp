package com.tinydeveloper.borutoapp.presentaion.screens.search

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tinydeveloper.borutoapp.presentaion.common.ListContent
import com.tinydeveloper.borutoapp.ui.theme.on_primary
import com.tinydeveloper.borutoapp.ui.theme.topAppBarBackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavHostController,
    searchViewModel: SearchViewModel = hiltViewModel()
){
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.topAppBarBackgroundColor)

    val heroes = searchViewModel.searchedHeroes.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SearchTopBar(
                text = searchViewModel.searchQuery.value,
                onTextChange = searchViewModel::updateSearchQuery,
                onSearchClicked = searchViewModel::searchHeroes ,
                onCloseClicked = navController::popBackStack
            )
        },
        content = {
            ListContent(heroes = heroes, navController = navController)
        }
    )
}