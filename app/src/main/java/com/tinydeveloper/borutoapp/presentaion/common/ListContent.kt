package com.tinydeveloper.borutoapp.presentaion.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.domain.model.Hero
import com.tinydeveloper.borutoapp.navigation.Screen
import com.tinydeveloper.borutoapp.presentaion.components.RatingWidget
import com.tinydeveloper.borutoapp.presentaion.components.ShimmerEffect
import com.tinydeveloper.borutoapp.ui.theme.*
import com.tinydeveloper.borutoapp.util.BASE_URL

@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
){
    val result = handlePagingResult(heroes = heroes)
    if (result){
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ){
            items(
                items = heroes,
                key = { hero-> hero.id }
            ){ hero ->
                hero?.let {
                    HeroItem(hero = hero, navController = navController)
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>
): Boolean{
    heroes.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }
        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                EmptyScreen(error = error, heroes = heroes)
                false
            }
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun HeroItem(
    hero:Hero,
    navController: NavHostController
){
    val painter = rememberImagePainter(BASE_URL + hero.image){
        placeholder(R.drawable.ic_placeholder)
        error(R.drawable.ic_placeholder)
    }
    Box(
        modifier= Modifier
            .height(HERO_ITEM_HEIGHT)
            .clip(shape = RoundedCornerShape(EXTRA_LARGE_PADDING))
            .clickable { navController.navigate(Screen.Details.passHeroId(heroId = hero.id)) },
        contentAlignment = Alignment.BottomStart
    ){
        Surface {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painter,
                contentDescription = stringResource(R.string.hero_item_image),
                contentScale = ContentScale.Crop
            )
        }
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            color = MaterialTheme.colors.caverColor
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.contentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Text(
                    text = hero.about,
                    color = HalfWhite,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(SMALL_PADDING))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        rating = hero.popularity,
                        scaleFactory = 2.2f,
                        direction = LayoutDirection.Rtl,
                        spaceBetween = EXTRA_EXTRA_SMALL_PADDING
                    )
                    Spacer(modifier = Modifier.width(EXTRA_SMALL_PADDING))
                    Text(
                        text = "(${hero.popularity})",
                        textAlign = TextAlign.Center,
                        fontSize = MaterialTheme.typography.body1.fontSize,
                        fontWeight = FontWeight.Bold,
                        color = HalfWhite
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun HeroItemPreview(){
    HeroItem(
        Hero(
            id = 1,
            name = "کیومرث",
            image = "/images/keuoomars.jpg",
            about = "کیومرث نخستين پادشاه و بنيانگذار سلسله پيشدادي در هزاران سال پيش . نام وي در اوستا گيومرتا آمده است و ذکر شده است که زرتشتيان او را نخستين انسان مي دانند . در زمان او مردم در غارها و کوهها بودند و بدن خود را با پوست حيوانات مي پوشاندند.",
            militaryRank = "پادشاه",
            popularity = 4.3,
            highlights = "تاسیس سلسله پیشدادی",
            ageOf = "پیشداد",
            year = "7000 سال ییش از میلاد",
            family = listOf("کوروش","داریوش","خشایار","جمشید","کمبوجیه","فریدون"),
            positives = listOf("تاسیس دارالفنون","برقراری عدالت","کشورگشایی","بهبود معیشت","برابری عدالت","پیروزی بر دشمنان"),
            negatives = listOf("نظامی گری","بد رفتاری","خوشکسالی","عدم رابطه با همسایگان","جنگ با همسایگان","غارت همسایگان")
        ),
        rememberNavController()
    )
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun HeroItemDarkPreview(){
    HeroItem(
        Hero(
            id = 1,
            name = "کیومرث",
            image = "/images/keuoomars.jpg",
            about = "کیومرث نخستين پادشاه و بنيانگذار سلسله پيشدادي در هزاران سال پيش . نام وي در اوستا گيومرتا آمده است و ذکر شده است که زرتشتيان او را نخستين انسان مي دانند . در زمان او مردم در غارها و کوهها بودند و بدن خود را با پوست حيوانات مي پوشاندند.",
            militaryRank = "پادشاه",
            popularity = 4.3,
            highlights = "تاسیس سلسله پیشدادی",
            ageOf = "پیشداد",
            year = "7000 سال ییش از میلاد",
            family = listOf("کوروش","داریوش","خشایار","جمشید","کمبوجیه","فریدون"),
            positives = listOf("تاسیس دارالفنون","برقراری عدالت","کشورگشایی","بهبود معیشت","برابری عدالت","پیروزی بر دشمنان"),
            negatives = listOf("نظامی گری","بد رفتاری","خوشکسالی","عدم رابطه با همسایگان","جنگ با همسایگان","غارت همسایگان")
        ),
        rememberNavController()
    )
}