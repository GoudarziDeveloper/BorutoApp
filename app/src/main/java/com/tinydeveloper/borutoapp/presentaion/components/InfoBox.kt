package com.tinydeveloper.borutoapp.presentaion.components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.tinydeveloper.borutoapp.R
import com.tinydeveloper.borutoapp.ui.theme.*

@Composable
fun InfoBox(
    modifier: Modifier,
    icon: Painter,
    iconColor: Color,
    bigText: String,
    smallText: String,
    textColor: Color
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .padding(all = EXTRA_SMALL_PADDING)
                .size(INFO_BOX_ICON_SIZE),
            painter = icon,
            contentDescription = stringResource(R.string.icfo_icon),
            tint = iconColor
        )
        Column {
            Text(
                text = bigText,
                color = textColor,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.subtitle1.fontSize
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun InfoBoxPreview(){
    InfoBox(
        modifier = Modifier,
        icon = painterResource(id = R.drawable.ic_king),
        iconColor = primary,
        bigText = "پادشاه",
        smallText = "جایگاه",
        textColor = MaterialTheme.colors.titleColor
    )
}

@Composable
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
fun InfoBoxDarkPreview(){
    InfoBox(
        modifier = Modifier,
        icon = painterResource(id = R.drawable.ic_king),
        iconColor = primary,
        bigText = "پادشاه",
        smallText = "جایگاه",
        textColor = MaterialTheme.colors.titleColor
    )
}