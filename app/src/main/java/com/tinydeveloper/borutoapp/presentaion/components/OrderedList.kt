package com.tinydeveloper.borutoapp.presentaion.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.tinydeveloper.borutoapp.ui.theme.EXTRA_SMALL_PADDING
import com.tinydeveloper.borutoapp.ui.theme.titleColor

@Composable
fun OrderedList(
    title: String,
    items: List<String>,
    textColor: Color,
    heroName:String? = null
){
    Column {
        Text(
            text = title,
            color = textColor,
            fontSize = MaterialTheme.typography.subtitle1.fontSize,
            fontWeight = FontWeight.Bold
        )
        items.forEachIndexed { index, item ->
            if (heroName == null){
                Text(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .padding(top = EXTRA_SMALL_PADDING),
                    text = "${index + 1}. $item",
                    color = textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    overflow = TextOverflow.Ellipsis
                )
            }else{
                Text(
                    modifier = Modifier
                        .alpha(if (item.trim() == heroName.trim()) ContentAlpha.high else ContentAlpha.medium)
                        .padding(top = EXTRA_SMALL_PADDING),
                    text = "${index + 1}. $item",
                    color = textColor,
                    fontSize = MaterialTheme.typography.body1.fontSize,
                    fontWeight = if (heroName.trim() == item.trim()) FontWeight.Bold else FontWeight.Medium,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun OrderedListPreview(){
    OrderedList(
        title = "خانواده",
        items = listOf("کوروش","داریوش","خشایار","جمشید","کمبوجیه","فریدون"),
        textColor = MaterialTheme.colors.titleColor
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun OrderedListDarkPreview(){
    OrderedList(
        title = "خانواده",
        items = listOf("کوروش","داریوش","خشایار","جمشید","کمبوجیه","فریدون"),
        textColor = MaterialTheme.colors.titleColor
    )
}