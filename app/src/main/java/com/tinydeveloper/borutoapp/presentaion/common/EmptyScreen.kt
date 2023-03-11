package com.tinydeveloper.borutoapp.presentaion.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.ui.theme.NETWORK_ERROR_ICON_SIZE
import com.tinydeveloper.borutoapp.ui.theme.SMALL_PADDING
import java.net.ConnectException
import java.net.SocketException
import java.net.SocketTimeoutException

@Composable
fun EmptyScreen(
    error: LoadState.Error? = null,
    heroes: LazyPagingItems<Hero>? = null
){
    var message by remember {
        mutableStateOf("قهرمان خود را بیابید")
    }
    var icon by remember {
        mutableStateOf(R.drawable.ic_search_heroes)
    }
    if (error != null){
        message = parseErrorMessage(error = error)
        icon = R.drawable.connection_error
    }
    var startAnimation by remember {
        mutableStateOf(false)
    }
    val alphaAnim by animateFloatAsState(
        targetValue = if (startAnimation) ContentAlpha.disabled else 0f,
        animationSpec = tween(
            durationMillis = 1000
        )
    )
    LaunchedEffect(key1 = true){
        startAnimation = true
    }
    EmptyContent(
        icon = icon,
        alphaAnim = alphaAnim,
        message = message,
        heroes = heroes,
        error = error
    )
}

@Composable
fun EmptyContent(
    icon: Int,
    alphaAnim: Float,
    message: String,
    heroes: LazyPagingItems<Hero>? = null,
    error: LoadState.Error? = null
){
    var isRefresh by remember { mutableStateOf(false) }
    SwipeRefresh(
        swipeEnabled = error != null,
        state = rememberSwipeRefreshState(isRefreshing = isRefresh),
        onRefresh = {
        isRefresh = true
        heroes?.refresh()
        isRefresh = false
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = stringResource(R.string.network_error),
                modifier = Modifier
                    .size(NETWORK_ERROR_ICON_SIZE)
                    .alpha(alphaAnim),
                tint = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = SMALL_PADDING)
                    .alpha(alphaAnim),
                text = message,
                color = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Medium,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}

fun parseErrorMessage(error: LoadState.Error): String {
    //return error.error.message.toString()
    return when(error.error) {
        is SocketTimeoutException -> {
            "سرور در دسترس نیست"
        }
        is ConnectException -> {
            "اینترنت در دسترس نیست"
        }
        else -> {
            "خطای نامشخص"
        }
    }
}

@Composable
@Preview(showBackground = true)
fun EmptyScreenPreview(){
    EmptyContent(icon = R.drawable.connection_error, alphaAnim = ContentAlpha.disabled, message = "ConnectException")
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun EmptyScreenDarkPreview(){
    EmptyContent(icon = R.drawable.connection_error, alphaAnim = ContentAlpha.disabled, message = "ConnectException")
}