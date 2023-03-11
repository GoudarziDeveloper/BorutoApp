package com.tinydeveloper.borutoapp.presentaion.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.navigation.Screen
import com.tinydeveloper.borutoapp.presentaion.common.HeroItem
import com.tinydeveloper.borutoapp.presentaion.common.ListContent
import com.tinydeveloper.borutoapp.presentaion.components.RatingWidget
import com.tinydeveloper.borutoapp.ui.theme.on_primary
import com.tinydeveloper.borutoapp.ui.theme.topAppBarBackgroundColor

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavHostController,
    homeViewModel: HomeScreenViewModel = hiltViewModel()
) {
    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = MaterialTheme.colors.topAppBarBackgroundColor)

    val allHeroes = homeViewModel.getAllHeroes.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            HomeTopBar(onSearchClicked = {
                navController.navigate(Screen.Search.route)
            })
        }
    ) {
        ListContent(heroes = allHeroes, navController = navController)
    }
}