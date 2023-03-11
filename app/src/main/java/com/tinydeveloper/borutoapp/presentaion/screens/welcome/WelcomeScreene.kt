package com.tinydeveloper.borutoapp.presentaion.screens.welcome

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.pager.*
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.domain.model.OnBoardingPage
import com.tinydeveloper.borutoapp.navigation.Screen
import com.tinydeveloper.borutoapp.ui.theme.*
import com.tinydeveloper.borutoapp.util.ON_BOARDING_PAGE_COUNT

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel = hiltViewModel()
){
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third
    )
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.welcomeScreenBackground)
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1.3f).fillMaxSize(),
            count = ON_BOARDING_PAGE_COUNT,
            state = pagerState,
            verticalAlignment = Alignment.Top
            //reverseLayout = true
        ) { pageNumber ->
            PagerScreen(pages[pageNumber])
        }
        HorizontalPagerIndicator(
            modifier = Modifier.weight(0.1f).align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colors.activeIndicator,
            inactiveColor = MaterialTheme.colors.inActiveIndicator,
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_SPACING
        )
        FinishButton(
            modifier = Modifier.weight(0.2f).padding(top = LARGE_PADDING),
            pagerState = pagerState
        ) {
            navController.popBackStack()
            navController.navigate(route = Screen.Home.route)
            welcomeViewModel.saveOnBoardingState(completed = true)
        }
    }
}

@Composable
private fun PagerScreen(onBoardingPage: OnBoardingPage){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = stringResource(R.string.boarding_image_desc),
            modifier = Modifier.fillMaxWidth(0.4f).fillMaxHeight(0.7f)
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = EXTRA_LARGE_PADDING),
            text = onBoardingPage.title,
            color = MaterialTheme.colors.titleColor,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.h4.fontSize,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = EXTRA_LARGE_PADDING).padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            color = MaterialTheme.colors.descriptionColor,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
){
    Row(
        modifier = modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == ON_BOARDING_PAGE_COUNT - 1
        ){
            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.buttonBackgroundColor,
                    contentColor = Color.White,
                ),
                shape = RoundedCornerShape(50)
            ){
                Text(
                    text = stringResource(R.string.start_using),
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FirstOnBoardingScreenPreview(){
    Column {
        PagerScreen(OnBoardingPage.First)
    }
}

@Preview(showBackground = true)
@Composable
private fun SecondOnBoardingScreenPreview(){
    Column {
        PagerScreen(OnBoardingPage.Second)
    }
}

@Preview(showBackground = true)
@Composable
private fun ThirdOnBoardingScreenPreview(){
    Column {
        PagerScreen(OnBoardingPage.Third)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun FirstOnBoardingScreenDarkPreview(){
    Column {
        PagerScreen(OnBoardingPage.First)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SecondOnBoardingScreenDarkPreview(){
    Column {
        PagerScreen(OnBoardingPage.Second)
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun ThirdOnBoardingScreenDarkPreview(){
    Column {
        PagerScreen(OnBoardingPage.Third)
    }
}

