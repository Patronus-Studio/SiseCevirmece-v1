package com.patronusstudio.sisecevirmece.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.applovin.sdk.AppLovinSdk
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.databinding.ActivityHomeBinding
import com.patronusstudio.sisecevirmece.enums.PlayStore
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import com.patronusstudio.sisecevirmece.util.extSayfaGecisi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val coroutine = CoroutineScope(Dispatchers.IO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        listener()
        sharedPrefControl()
        createBannerAd()
    }


    private fun sharedPrefControl() {
        newAppDialog()
        val sharedPref = SharedVeriSaklama(this)
        val isShowed = sharedPref.getBottleFlip2Dialog()
        if (isShowed.not()) {
            newAppDialog()
            sharedPref.putBottleFlip2Dialog()
        }
    }

    private fun newAppDialog() {
        val dialog = DialogNewAppFragment()
        dialog.show(supportFragmentManager, "")
    }

    private fun listener() {
        binding.imgBeer.setOnClickListener {
            this.extSayfaGecisi(SiseDondurmeActivity::class.java)
        }
        binding.imgSoru.setOnClickListener {
            this.extSayfaGecisi(SorulariGoruntuleSecimEkrani::class.java)
        }
        binding.imgAyarlar.setOnClickListener {
            this.extSayfaGecisi(AyarlarActivity::class.java)
        }
        binding.imgStore.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(PlayStore.SISE_CEVIRMECE_1.isim))
            startActivity(intent)
        }
        binding.imgApps.setOnClickListener {
            intent = Intent(Intent.ACTION_VIEW, Uri.parse(PlayStore.TUM_UYGULAMALAR.isim))
            startActivity(intent)
        }
    }

    private fun createBannerAd() {
        binding.adviewBanner.loadAd()
    }
}

