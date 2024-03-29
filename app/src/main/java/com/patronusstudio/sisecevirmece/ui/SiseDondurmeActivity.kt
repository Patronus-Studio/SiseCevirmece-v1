package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.binding.SiseDondurmeOnClickBinding
import com.patronusstudio.sisecevirmece.databinding.ActivitySiseDondurmeBinding
import com.patronusstudio.sisecevirmece.util.CustomTimer
import com.patronusstudio.sisecevirmece.util.DrinkUtils
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.extSayfaGecisi

class SiseDondurmeActivity : AppCompatActivity() {


    private lateinit var binding: ActivitySiseDondurmeBinding
    private lateinit var customTimer: CustomTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sise_dondurme)
        imgKontrol()

        val sayacBitince = {
            this.extSayfaGecisi(SecimEkraniActivity::class.java)
        }

        customTimer = CustomTimer(sayacBitince)
        binding.sise = SiseDondurmeOnClickBinding()
        binding.customTimer = customTimer

        binding.adviewBanner.loadAd()

    }

    private fun imgKontrol() {

        val imageId = OyunIslemleri.drinks[DrinkUtils().getSelectedSiseTuru().id].imageId
        Glide.with(this).load(imageId).into(binding.siseDonen)
    }

    override fun onResume() {
        super.onResume()
        binding.siseDonen.isEnabled = true
        binding.adviewBanner.visibility = View.VISIBLE
    }

    override fun onPause() {
        super.onPause()
        binding.adviewBanner.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        super.onBackPressed()
        customTimer.sayacDurdur()
    }


}


