package com.patronusstudio.sisecevirmece.util

import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.model.DrinkType

object OyunIslemleri {
    var dogrulukSize = 0
    var cesaretSize = 0

    var dogrulukLastValue = 1
    var cesaretLastValue = 1


    var dialogButonunaBasildiMi = false

    var soruSilindiMi = false
    var soruGuncellendiMi = false
    var soruEklendiMi = false
    var degisenSoruIndexi = 0
    var guncellenenSoru = ""

    val drinks = listOf(
        DrinkType(0, "Kola", R.drawable.cola, false),
        DrinkType(1, "Viski", R.drawable.whisky, false),
        DrinkType(2, "Şarap", R.drawable.wine, false),
        DrinkType(3, "Bira", R.drawable.beer, false)
    )
}