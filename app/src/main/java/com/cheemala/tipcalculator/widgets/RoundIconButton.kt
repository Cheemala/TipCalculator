package com.cheemala.tipcalculator.widgets

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val OnBtnClickedAnimation = Modifier.size(40.dp)

@Composable
fun RoundIconBtn(
    modifier: Modifier = Modifier,
    imageDrawable: Int,
    onClick: () -> Unit,
    tint: Color = Color.Black.copy(alpha = 0.8f),
    backgroundColor: Color = Color.White, elevation: Dp = 5.dp
) {

    Card(modifier = Modifier
        .padding(all = 4.dp)
        .clickable { onClick.invoke() }
        .then(OnBtnClickedAnimation),
        shape = CircleShape, backgroundColor = backgroundColor, elevation = elevation) {
        Image(painter = painterResource(id = imageDrawable), contentDescription = "plus or minus")
    }
}