package com.tinydeveloper.borutoapp.presentaion.screens.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.tinydeveloper.borutoapp.util.BASE_URL
import com.tinydeveloper.borutoapp.util.PaletteGenerator.convertImageUrlToBitmap
import com.tinydeveloper.borutoapp.util.PaletteGenerator.extractColorFromBitmap
import kotlinx.coroutines.flow.collectLatest

@Composable
fun DetailsScreen(
    navController: NavHostController,
    detailsViewModel: DetailsViewModel = hiltViewModel()
){
    val hero by detailsViewModel.selectedHero.collectAsState()
    val colorPalette by detailsViewModel.colorPalette

    if (colorPalette.isNotEmpty()){
        ContentDetails(navHostController = navController, selectedHero = hero, colors = colorPalette)
    } else{
        detailsViewModel.generateColorPallet()
    }
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        detailsViewModel.uiEvent.collectLatest { event ->
            when(event){
                is UiEvent.GenerateColorPallet -> {
                    val bitmap = convertImageUrlToBitmap(
                        imageUrl = "$BASE_URL${hero?.image}",
                        context = context
                    )
                    if (bitmap != null){
                        detailsViewModel.setColorPalette(
                            colorPalette = extractColorFromBitmap(
                                bitmap = bitmap
                            )
                        )
                    }
                }
            }
        }
    }
}