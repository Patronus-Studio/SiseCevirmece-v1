package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.model.DrinkType
import com.patronusstudio.sisecevirmece.model.compareAndTransform
import com.patronusstudio.sisecevirmece.util.AppColor

// TODO: compose ekranına çevirebiliriz
class AyarlarActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(modifier = Modifier.fillMaxSize()) {
                Spacer(modifier = Modifier.height(16.dp))
                BottleTypesCard()
            }
        }
    }

    @Preview
    @Composable
    private fun BottleTypesCard() {
        val drinks = remember {
            mutableStateOf(
                listOf(
                    DrinkType(1, "Kola", R.drawable.cola, true),
                    DrinkType(2, "Bira", R.drawable.beer, false),
                    DrinkType(3, "Viski", R.drawable.whisky, false),
                    DrinkType(4, "Şarap", R.drawable.wine, false)
                )
            )
        }
        val roundedShape = RoundedCornerShape(32.dp)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(180.dp)
                    .clip(roundedShape)
                    .background(
                        AppColor.Black.copy(alpha = 0.1f), roundedShape
                    ), contentAlignment = Alignment.Center
            ) {
                LazyRow {
                    item {
                        Spacer(modifier = Modifier.width(16.dp))
                    }
                    val gradientColor =
                        listOf(AppColor.Green, AppColor.GreenLight, AppColor.YellowLight)
                    items(drinks.value.size) {
                        Bottle(drinks.value[it], gradientColor) {
                            drinks.value = drinks.value.compareAndTransform(drinks.value[it].id)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Bottle(
        drinkType: DrinkType, gradientColor: List<Color>, clicked: () -> Unit
    ) {
        val boxModifier = if (drinkType.isSelected) {
            Modifier.border(
                2.dp,
                Brush.horizontalGradient(gradientColor),
                RoundedCornerShape(16.dp),
            )
        } else Modifier
        Box(modifier = boxModifier.clickable { clicked() }) {
            Column(
                Modifier
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    drinkType.imageId,
                    contentDescription = "${drinkType.name} Şişesi",
                    modifier = Modifier.size(120.dp)
                )
                Text(text = drinkType.name)
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }

}



