package com.patronusstudio.sisecevirmece.util

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.patronusstudio.sisecevirmece.R

@Composable
fun LoadingAnimation() {
    val state = rememberInfiniteTransition()
    val anim = state.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(animation = tween(1500))
    )
    Dialog(onDismissRequest = {}) {
        Surface(
            modifier = Modifier
                .size(120.dp)
                .rotate(
                    anim.value
                ), elevation = 4.dp, shape = CircleShape, color = AppColor.Black
        ) {
            AsyncImage(
                model = R.drawable.splasshimage, contentDescription = ""
            )
        }
    }
}