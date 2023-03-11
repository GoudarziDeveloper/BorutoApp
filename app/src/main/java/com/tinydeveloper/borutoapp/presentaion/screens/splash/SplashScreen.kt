package com.tinydeveloper.borutoapp.presentaion.screens.splash

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.navigation.Screen
import com.tinydeveloper.borutoapp.ui.theme.dark_primary
import com.tinydeveloper.borutoapp.ui.theme.dark_primary_dark
import com.tinydeveloper.borutoapp.ui.theme.on_primary
import com.tinydeveloper.borutoapp.ui.theme.second_on_primary
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    navController: NavHostController,
    splashViewModel: SplashViewModel = hiltViewModel()
){
    val onBoardingCompleted by splashViewModel.readOnBoardingState.collectAsState()
    val alpha = remember {
        Animatable(0f)
    }
    LaunchedEffect(key1 = true){
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1500,
                delayMillis = 500
            )
        )
        navController.popBackStack()
        if (onBoardingCompleted){
            navController.navigate(route = Screen.Home.route)
        }else {
            navController.navigate(route = Screen.Welcome.route)
        }
    }
    SplashController(alpha = alpha.value)
}

@Composable
private fun SplashController(alpha: Float){
    if (isSystemInDarkTheme()){
        Splash(modifier = Modifier.background(Brush.verticalGradient(
            listOf(dark_primary_dark, dark_primary))),
            alpha = alpha
        )
    }else {
        Splash(
            modifier = Modifier.background(Brush.verticalGradient(
                listOf(on_primary, second_on_primary))
            ),
            alpha = alpha
        )
    }
}

@Composable
private fun Splash(modifier: Modifier, alpha: Float){
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
    ){
        Image(
            painter = painterResource(R.drawable.ic_app_logo),
            contentDescription = stringResource(R.string.app_logo_description),
            modifier = Modifier.fillMaxWidth(0.3f).alpha(alpha),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
@Preview
private fun SplashLightPreview(){
    SplashController(alpha = 0f)
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun SplashDarkPreview(){
    SplashController(alpha = 0f)
}