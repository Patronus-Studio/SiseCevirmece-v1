package com.patronusstudio.sisecevirmece.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.patronusstudio.sisecevirmece.BuildConfig
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.model.DrinkType
import com.patronusstudio.sisecevirmece.util.AppColor
import com.patronusstudio.sisecevirmece.util.ApplovinUtils
import com.patronusstudio.sisecevirmece.util.DrinkUtils
import com.patronusstudio.sisecevirmece.util.LoadingAnimation
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import kotlinx.coroutines.delay

class AyarlarActivity : ComponentActivity() {

    private val sharedPref by lazy {
        SharedVeriSaklama(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isLoading = remember {
                mutableStateOf(false)
            }
            Background()
            Column(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly,
            ) {
                BottleTypesCard(adLoading = {
                    isLoading.value = true
                }, adShowed = {
                    isLoading.value = false
                })
                ResetGame()
                VersionCard()
            }
            if (isLoading.value) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    LoadingAnimation()
                }
            }
        }
    }

    @Composable
    private fun Background() {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                model = R.drawable.floor,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }

    @Composable
    private fun BottleTypesCard(adLoading: () -> Unit, adShowed: () -> Unit) {
        val context = LocalContext.current
        val drinks = remember { mutableStateOf(OyunIslemleri.drinks) }
        drinks.value[DrinkUtils().getSelectedSiseTuru().id].isSelected = true
        val roundedShape = RoundedCornerShape(32.dp)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .fillMaxWidth(0.9f)
                    .height(230.dp)
                    .clip(roundedShape)
                    .background(
                        AppColor.Black.copy(alpha = 0.25f), roundedShape
                    )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Row {
                        Spacer(modifier = Modifier.width(16.dp))
                        Text(
                            text = stringResource(R.string.bottle_type),
                            fontWeight = FontWeight.SemiBold,
                            color = AppColor.White,
                            fontSize = 20.sp,
                        )
                    }
                    LazyRow {
                        item {
                            Spacer(modifier = Modifier.width(16.dp))
                        }
                        val gradientColor =
                            listOf(AppColor.Green, AppColor.GreenLight, AppColor.YellowLight)
                        items(drinks.value.size) { indeks ->
                            Bottle(drinks.value[indeks], gradientColor) {
                                adLoading()
                                showInterstitialAd(adShowed = adShowed, adClosed = {
                                    drinks.value = DrinkUtils().compareAndTransform(
                                        drinks.value, drinks.value[indeks].id
                                    )
                                    DrinkUtils().setSelectedDrinks(context, drinks.value[indeks].id)
                                })
                            }
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
                Brush.linearGradient(gradientColor),
                RoundedCornerShape(16.dp),
            )
        } else Modifier
        Box(modifier = boxModifier) {
            Column(
                Modifier
                    .fillMaxHeight(0.7f)
                    .fillMaxWidth()
                    .clickable { clicked() },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                AsyncImage(
                    drinkType.imageId,
                    contentDescription = "${drinkType.name} Şişesi",
                    modifier = Modifier.size(80.dp)
                )
                Text(
                    text = drinkType.name,
                    color = AppColor.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
    }

    private fun showInterstitialAd(adShowed: () -> Unit, adClosed: () -> Unit) {
        ApplovinUtils.createInterstitialAd(
            this@AyarlarActivity, "4574d3ead6ec0cce", onAdLoaded = { ad ->
                ad.showAd()
            }, onAdShowed = adShowed, onAdClosed = adClosed
        )
    }

    @Composable
    private fun ResetGame() {
        val checkedStatus = remember { mutableStateOf(false) }
        LaunchedEffect(key1 = checkedStatus.value, block = {
            if (checkedStatus.value) {
                val resetActivityIntent =
                    Intent(this@AyarlarActivity, OyunSifirlaActivity::class.java)
                startActivity(resetActivityIntent)
                delay(1000L)
                checkedStatus.value = false
            }
        })
        val roundedShape = RoundedCornerShape(32.dp)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .fillMaxWidth(0.9f)
                    .clip(roundedShape)
                    .background(
                        AppColor.Black.copy(alpha = 0.25f), roundedShape
                    )
                    .padding(16.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.reset_game),
                        fontWeight = FontWeight.SemiBold,
                        color = AppColor.White,
                        fontSize = 20.sp
                    )
                    Switch(checked = checkedStatus.value, onCheckedChange = {
                        checkedStatus.value = checkedStatus.value.not()
                    })
                }
            }
        }
    }

    @Composable
    private fun VersionCard() {
        val roundedShape = RoundedCornerShape(32.dp)
        Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Box(
                Modifier
                    .fillMaxWidth(0.9f)
                    .clip(roundedShape)
                    .background(
                        AppColor.Black.copy(alpha = 0.25f), roundedShape
                    )
                    .padding(16.dp), contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Version " + BuildConfig.VERSION_NAME,
                    fontWeight = FontWeight.Bold,
                    color = AppColor.White,
                    fontSize = 16.sp
                )
            }
        }

    }
}



