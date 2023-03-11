package com.tinydeveloper.borutoapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.tinydeveloper.borutoapp.presentaion.screens.details.DetailsScreen
import com.tinydeveloper.borutoapp.presentaion.screens.home.HomeScreen
import com.tinydeveloper.borutoapp.presentaion.screens.search.SearchScreen
import com.tinydeveloper.borutoapp.presentaion.screens.splash.SplashScreen
import com.tinydeveloper.borutoapp.presentaion.screens.welcome.WelcomeScreen
import com.tinydeveloper.borutoapp.util.DETAILS_ARGUMENT_KEY

@Composable
fun SetupNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ){
        composable(route = Screen.Splash.route){
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Welcome.route){
            WelcomeScreen(navController = navController)
        }
        composable(route = Screen.Home.route){
            HomeScreen(navController = navController)
        }
        composable(
            route = Screen.Details.route,
            arguments = listOf(navArgument(DETAILS_ARGUMENT_KEY){ type = NavType.IntType })
        ){
            DetailsScreen(navController = navController)
        }
        composable(route = Screen.Search.route){
            SearchScreen(navController = navController)
        }
    }
}