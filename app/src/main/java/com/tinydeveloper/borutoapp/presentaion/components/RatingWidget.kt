package com.tinydeveloper.borutoapp.presentaion.components


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.tinydeveloper.borutoapp.ui.theme.EmptyStar
import com.tinydeveloper.borutoapp.ui.theme.LARGE_PADDING
import com.tinydeveloper.borutoapp.ui.theme.Star
import com.tinydeveloper.borutoapp.util.STARS_WIDGET_MAX_STARS

@Composable
fun RatingWidget(
    modifier: Modifier = Modifier,
    rating: Double,
    scaleFactory: Float = 3f,
    spaceBetween: Dp = EXTRA_SMALL_PADDING,
    direction: LayoutDirection = LayoutDirection.Ltr
){
    val starsState = calculateStars(rating = rating, maxStars = STARS_WIDGET_MAX_STARS)

    val starOathString = stringResource(R.string.star_path)
    val starPath = remember {
        PathParser().parsePathString(pathData = starOathString).toPath()
    }
    val starPathBounds = remember {
        starPath.getBounds()
    }
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(spaceBetween * scaleFactory)
    ){
        starsState["filledStars"]?.let {
            repeat(it){
                DirectionStar(starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactory = scaleFactory,
                    filled = true,
                    direction = direction
                )
            }
        }
        starsState["halfFilledStars"]?.let {
            if (it != 0){
                DirectionStar(starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactory = scaleFactory,
                    fillPercent = it * 10,
                    filled = direction == LayoutDirection.Rtl,
                    direction = direction
                )
            }
        }
        starsState["emptyStars"]?.let {
            repeat(it){
                DirectionStar(starPath = starPath,
                    starPathBounds = starPathBounds,
                    scaleFactory = scaleFactory,
                    filled = false,
                    direction = direction
                )
            }
        }
    }
}

@Composable
fun DirectionStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactory: Float,
    fillPercent: Int = 0,
    filled: Boolean = false,
    direction: LayoutDirection
){
    if (direction == LayoutDirection.Rtl){
        CustomRtlStar(
            starPath = starPath,
            starPathBounds = starPathBounds,
            scaleFactory = scaleFactory,
            fillPercent = fillPercent,
            filled = filled
        )
    }else{
        CustomLtrStar(
            starPath = starPath,
            starPathBounds = starPathBounds,
            scaleFactory = scaleFactory,
            fillPercent = fillPercent,
            filled = filled
        )
    }
}

@Composable
fun CustomLtrStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactory: Float,
    fillPercent: Int = 0,
    filled: Boolean = false
){
    val percent = when(fillPercent){
        in 1..10 -> { 3.3f }
        in 11..20 -> { 2.7f }
        in 21..30 -> { 2.1f }
        in 31..40 -> { 1.8f }
        in 41..49 -> { 1.7f }
        50 -> { 1.66f }
        in 51..60 -> { 1.53f }
        in 61..70 -> { 1.35f }
        in 71..80 -> { 1.2f }
        in 81..90 -> { 1f }
        in 91..99 -> { 0.9f }
        100 -> 0.8f
        else -> 0f
    }
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactory){
            val left = (canvasSize.width / 2) - (starPathBounds.width / 1.66f)
            val top = (canvasSize.height / 2) - (starPathBounds.height / 1.66f)
            translate(left = left, top = top){
                drawPath(
                    path = starPath,
                    color = if(filled) Star else EmptyStar
                )
                if (percent != 0f){
                    clipPath(path = starPath){
                        drawRect(
                            color = Star,
                            size = Size(
                                width = starPathBounds.maxDimension / percent,
                                height = starPathBounds.maxDimension * scaleFactory
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CustomRtlStar(
    starPath: Path,
    starPathBounds: Rect,
    scaleFactory: Float,
    fillPercent: Int = 0,
    filled: Boolean = true
){
    val percent = when(fillPercent){
        in 91..99 -> { 3.6f }
        in 81..90 -> { 3.2f }
        in 71..80 -> { 2.5f }
        in 61..70 -> { 2f }
        in 51..60 -> { 1.8f }
        50 -> { 1.66f }
        in 41..49 -> { 1.6f }
        in 31..40 -> { 1.5f }
        in 21..30 -> { 1.35f }
        in 11..20 -> { 1.25f }
        in 1..10 -> { 1.1f }
        100 -> 3.3f
        else -> 0f
    }
    Canvas(modifier = Modifier.size(24.dp)) {
        val canvasSize = size
        scale(scale = scaleFactory){
            val left = (canvasSize.width / 2) - (starPathBounds.width / 1.66f)
            val top = (canvasSize.height / 2) - (starPathBounds.height / 1.66f)
            translate(left = left, top = top){
                drawPath(
                    path = starPath,
                    color = if(filled) Star else EmptyStar
                )
                if (percent != 0f){
                    clipPath(path = starPath){
                        drawRect(
                            color = EmptyStar,
                            size = Size(
                                width = starPathBounds.maxDimension / percent,
                                height = starPathBounds.maxDimension * scaleFactory
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun calculateStars(rating: Double, maxStars:Int): Map<String, Int>{
    var filledStars by remember { mutableStateOf(0) }
    var halfFilledStars by remember { mutableStateOf(0) }
    var emptyStars by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = rating){
        val (firstNumber, lastNumber) = rating.toString().split(".").map { it.toInt() }
        if (firstNumber in 0..maxStars && lastNumber in 0..9){
            filledStars = firstNumber
            halfFilledStars = lastNumber
            if (filledStars == 5 && lastNumber > 0){
                emptyStars = 5
                filledStars = 0
                halfFilledStars = 0
            }
        }
        val halfCount = if (halfFilledStars > 0) 1 else 0
        emptyStars = maxStars - (filledStars + halfCount)
        println(emptyStars)
    }
    return mapOf(
        "filledStars" to filledStars,
        "halfFilledStars" to halfFilledStars,
        "emptyStars" to emptyStars
    )
}

@Composable
@Preview(showBackground = true)
fun HalfFilledStarsPreview(){
    RatingWidget(
        modifier = Modifier,
        rating = 3.5,
        scaleFactory = 3f,
        direction = LayoutDirection.Ltr
    )
}