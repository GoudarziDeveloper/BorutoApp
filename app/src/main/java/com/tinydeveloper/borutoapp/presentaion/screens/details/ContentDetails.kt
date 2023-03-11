package com.tinydeveloper.borutoapp.presentaion.screens.details

import android.graphics.Color.parseColor
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.presentaion.components.InfoBox
import com.tinydeveloper.borutoapp.presentaion.components.OrderedList
import com.tinydeveloper.borutoapp.ui.theme.*
import com.tinydeveloper.borutoapp.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentDetails(
    navHostController: NavHostController,
    selectedHero: Hero?,
    colors: Map<String, String>
){
    var vibrant by  remember{ mutableStateOf("#000000") }
    var darkVibrant by remember{ mutableStateOf("#000000") }
    var onDarkVibrant by remember{ mutableStateOf("#ffffff") }

    LaunchedEffect(key1 = selectedHero){
        vibrant = colors[VIBRANT_COLOR]?: vibrant
        darkVibrant = colors[DARK_VIBRANT_COLOR]?: darkVibrant
        onDarkVibrant = colors[ON_DARK_VIBRANT_COLOR]?: onDarkVibrant
    }

    val systemUiController = rememberSystemUiController()
    systemUiController.setStatusBarColor(color = Color(parseColor(darkVibrant)))

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Expanded)
    )
    val radiusAnim by animateDpAsState(
        targetValue =
            if (scaffoldState.currentFraction == 1f) EXTRA_LARGE_PADDING
            else BOTTOM_SHEET_EXPENDED_RADIUS
    )
    var isExpendedDescription by remember {
        mutableStateOf(false)
    }
    BottomSheetScaffold(
        sheetShape = RoundedCornerShape(topStart = radiusAnim, topEnd = radiusAnim),
        scaffoldState = scaffoldState,
        sheetPeekHeight = MIN_SHEET_HEIGHT,
        sheetContent = {
            selectedHero?.let {
                if (isExpendedDescription)
                    BottomSheetDescription(
                        heroName = selectedHero.name,
                        description = selectedHero.about,
                        contentColor = Color(parseColor(onDarkVibrant)),
                        sheetBackgroundColor = Color(parseColor(darkVibrant)),
                        closeDescription = { isExpendedDescription = false}
                    )
                else
                    BottomSheetContent(
                        selectedHero = it,
                        expendDescription = { isExpendedDescription = true},
                        infoBoxIconColor = Color(parseColor(vibrant)),
                        sheetBackgroundColor = Color(parseColor(darkVibrant)),
                        contentColor = Color(parseColor(onDarkVibrant))
                    )
            }
        },
        content = {
             selectedHero?.let {
                 BackgroundContent(
                     heroImage = it.image,
                     imageFraction = scaffoldState.currentFraction,
                     backgroundColor = Color(parseColor(darkVibrant)),
                     closeIconTint = Color(parseColor(darkVibrant)),
                     closeClicked = { navHostController.popBackStack() }
                 )
             }
        }
    )
}

@Composable
fun BottomSheetDescription(
    heroName: String,
    description: String,
    contentColor: Color = MaterialTheme.colors.titleColor,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    closeDescription: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxWidth()
        .background(sheetBackgroundColor)
        .padding(all = LARGE_PADDING)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(BOTTOM_SHEET_ICON_SIZE)
                    .weight(1f),
                painter = painterResource(id = R.drawable.ic_icon),
                contentDescription = stringResource(R.string.app_icon),
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(8f),
                text = heroName,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.description_title),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            IconButton(onClick = closeDescription) {
                Icon(
                    modifier = Modifier.size(EXPENDED_ICON_SIZE),
                    painter = painterResource(id = R.drawable.ic_minimize),
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = contentColor
                )
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(BOTTOM_SHEET_DESCRIPTION)) {
            item {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = description,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = contentColor
                )
            }
        }
    }
}

private fun calFraction(fraction: Float): Float{
    val fractionPercent = fraction * 100
    return fractionPercent * MIN_BACKGROUND_DETAILS / 100
}

@OptIn(ExperimentalMaterialApi::class)
val BottomSheetScaffoldState.currentFraction: Float get() {
    val fraction = bottomSheetState.progress.fraction
    val currentValue = bottomSheetState.currentValue
    val targetValue = bottomSheetState.targetValue
    return when{
        currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 1f
        currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 0f
        currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Collapsed -> 0f + fraction
        currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> 1f - fraction
        else -> fraction
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BackgroundContent(
    heroImage: String,
    imageFraction: Float = 1f,
    backgroundColor: Color = MaterialTheme.colors.surface,
    closeIconTint: Color = Color.White,
    closeClicked: ()-> Unit
){
    val imagePainter = rememberImagePainter("$BASE_URL$heroImage"){
        error(R.drawable.ic_placeholder)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
    ){
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(imageFraction + (MIN_BACKGROUND_DETAILS - calFraction(imageFraction)))
                .align(Alignment.TopStart),
            painter = imagePainter,
            contentDescription = stringResource(id = R.string.hero_item_image),
            contentScale = ContentScale.Crop
        )
        IconButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(all = SMALL_PADDING),
            onClick = closeClicked) {
            Icon(
                modifier = Modifier.size(CLOSE_ICON_SIZE),
                imageVector = Icons.Default.Close,
                contentDescription = stringResource(id = R.string.close_icon),
                tint = closeIconTint
            )
        }
    }
}

@Composable
fun BottomSheetContent(
    selectedHero: Hero,
    infoBoxIconColor: Color = Color.Black,
    contentColor: Color = MaterialTheme.colors.titleColor,
    sheetBackgroundColor: Color = MaterialTheme.colors.surface,
    expendDescription: () -> Unit
){
    Column(
        modifier = Modifier
            .background(sheetBackgroundColor)
            .padding(all = LARGE_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(BOTTOM_SHEET_ICON_SIZE)
                    .weight(1f),
                painter = painterResource(id = R.drawable.ic_icon),
                contentDescription = stringResource(R.string.app_icon),
                tint = contentColor
            )
            Text(
                modifier = Modifier.weight(8f),
                text = selectedHero.name,
                color = contentColor,
                fontSize = MaterialTheme.typography.h4.fontSize,
                fontWeight = FontWeight.Bold
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = LARGE_PADDING),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            InfoBox(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.ic_king2),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.militaryRank,
                smallText = stringResource(R.string.king_title),
                textColor = contentColor
            )
            InfoBox(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.ic_gov2),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.ageOf,
                smallText = stringResource(R.string.gov_title),
                textColor = contentColor
            )
            InfoBox(
                modifier = Modifier.weight(1f),
                icon = painterResource(id = R.drawable.ic_cake),
                iconColor = infoBoxIconColor,
                bigText = selectedHero.year,
                smallText = stringResource(R.string.year_title),
                textColor = contentColor
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.description_title),
                fontSize = MaterialTheme.typography.subtitle1.fontSize,
                fontWeight = FontWeight.Bold,
                color = contentColor
            )
            IconButton(onClick = expendDescription) {
                Icon(
                    modifier = Modifier.size(EXPENDED_ICON_SIZE),
                    painter = painterResource(id = R.drawable.ic_expend),
                    contentDescription = stringResource(id = R.string.close_icon),
                    tint = contentColor
                )
            }
        }
        LazyColumn(modifier = Modifier
            .fillMaxWidth()
            .height(ABOUT_LIST_HEIGHT)
            .padding(bottom = LARGE_PADDING)) {
            item {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .alpha(ContentAlpha.medium)
                        .padding(bottom = MEDIUM_PADDING),
                    text = selectedHero.about,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    color = contentColor
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderedList(
                title = stringResource(R.string.family_title),
                items = selectedHero.family,
                textColor = contentColor,
                heroName = selectedHero.name
            )
            OrderedList(
                title = stringResource(R.string.positives_title),
                items = selectedHero.positives,
                textColor = contentColor
            )
            OrderedList(
                title = stringResource(R.string.negatives_title),
                items = selectedHero.negatives,
                textColor = contentColor
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun DetailsContentPreview(){
    BottomSheetContent(selectedHero =
        Hero(
            id = 13,
            name = "سورنا",
            image = "/images/soorena.jpg",
            about = "سورنا یکی از سرداران بزرگ و نامدار تاریخ است که سپاه ایران را در نخستین جنگ ایران با رومیان را فرماندهی کرد و رومی ها را که تا آن زمان قسمتی از ارمنستان و آذرآبادگان را تصرف نموده بودند، را در جنک کاره به سختی شکست داد به طوری که لژیون های قدرتمند رومی که در آن زمان بزرگترین و زبده ترین ارتش جهان باستان به شمار می رفتند، به طور کامال شکست خوردند و بیش از 20 هزار تن کشته دادند.",
            militaryRank = "فرمانده",
            popularity = 4.9,
            highlights = "جنگ با امپراتوری روم",
            ageOf = "اشکانیان",
            year = "1900 ق.م",
            family = listOf("کوروش","داریوش","خشایار","جمشید","کمبوجیه","فریدون"),
            positives = listOf("تاسیس دارالفنون","برقراری عدالت","کشورگشایی","بهبود معیشت","برابری عدالت","پیروزی بر دشمنان"),
            negatives = listOf("نظامی گری","بد رفتاری","خوشکسالی","جدال با همسایگان","جنگ با همسایگان","غارت همسایگان")
        )
    ){}
}