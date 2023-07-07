package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.abstracts.CesaretDatabase
import com.patronusstudio.sisecevirmece.abstracts.DogrulukDatabase
import com.patronusstudio.sisecevirmece.databinding.ActivitySoruEkleBinding
import com.patronusstudio.sisecevirmece.enums.DogrulukCesaret
import com.patronusstudio.sisecevirmece.model.CesaretModel
import com.patronusstudio.sisecevirmece.model.DogrulukModel
import com.patronusstudio.sisecevirmece.util.ApplovinUtils
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import com.patronusstudio.sisecevirmece.util.extStatusBarColor

class SoruEkleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoruEkleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_soru_ekle)
        this extStatusBarColor "#01A555"

        val getBooleanIntent = intent.getBooleanExtra(DogrulukCesaret.DOGRULUK_CESARET.isim, false)

        binding.butonSoruEkle.setOnClickListener {
            val girilenSoru = binding.edxGirilenSoru.text
            textControl(girilenSoru, getBooleanIntent)
        }
    }

    private fun textControl(girilenSoru: Editable?, getBooleanIntent: Boolean){
        if (!girilenSoru.isNullOrBlank()) {
            ApplovinUtils.createInterstitialAd(this, "0f2225785dad5aba", onAdClosed = {
                val sharedPref = SharedVeriSaklama(this)
                if (getBooleanIntent) {
                    val model = DogrulukModel(soru = girilenSoru.toString())
                    DogrulukDatabase.getDatabaseManager(this).dogrulukDao().insert(model)
                    OyunIslemleri.dogrulukSize++
                    sharedPref.updateDogrulukSize(OyunIslemleri.dogrulukSize)
                } else {
                    val model = CesaretModel(soru = girilenSoru.toString())
                    CesaretDatabase.getDatabaseManager(this).cesaretDao().insert(model)
                    OyunIslemleri.cesaretSize++
                    sharedPref.updateCesaretSize(OyunIslemleri.cesaretSize)
                }
                OyunIslemleri.guncellenenSoru = girilenSoru.toString()
                OyunIslemleri.soruEklendiMi = true
                Toast.makeText(this, getString(R.string.soru_kayit_basarili), Toast.LENGTH_SHORT)
                    .show()
                finish()
            }, onAdLoaded = {
                it.showAd()
            })
            true
        } else Toast.makeText(this, getString(R.string.soru_kayit_hatali), Toast.LENGTH_SHORT)
            .show()

    }
}
