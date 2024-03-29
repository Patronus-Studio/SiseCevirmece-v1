package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.abstracts.CesaretDatabase
import com.patronusstudio.sisecevirmece.abstracts.DogrulukDatabase
import com.patronusstudio.sisecevirmece.databinding.ActivitySoruBinding
import com.patronusstudio.sisecevirmece.enums.DogrulukCesaret
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import com.patronusstudio.sisecevirmece.util.extStatusBarColor

class SoruActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoruBinding
    private lateinit var gosterilenSoru: String
    private var getBooleanIntent: Boolean = false
    private val cesaretDatabase by lazy {
        CesaretDatabase.getDatabaseManager(this)
    }
    private val dogrulukDatabase by lazy {
        DogrulukDatabase.getDatabaseManager(this)
    }

    private val model by lazy {
        SharedVeriSaklama(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_soru)
        this extStatusBarColor "#00000000"
        getBooleanIntent = intent.getBooleanExtra(DogrulukCesaret.DOGRULUK_CESARET.isim, false)
        soruSayisiKontrol(getBooleanIntent)
        bindingText()
        sharedGuncelle()
        listener()
        binding.adviewBanner.loadAd()
    }

    private fun listener(){
        binding.ortaCard.soruCevaplandi.setOnClickListener {
            finish()
        }
        binding.ortaCard.soruSoruDegistir.setOnClickListener {
            soruSayisiKontrol(getBooleanIntent)
            bindingText()
            sharedGuncelle()
        }
        binding.altCard.soruBenSoracagim.setOnClickListener {
            finish()
        }
    }


    private fun bindingText() {
        binding.ustCard.cardSoru.text = gosterilenSoru
    }

    private fun soruSayisiKontrol(dogrulukMu: Boolean) {
        if (OyunIslemleri.cesaretLastValue > OyunIslemleri.cesaretSize) {
            cesaretDatabase.cesaretDao().updateAll()
            OyunIslemleri.cesaretLastValue = 1
        } else if (OyunIslemleri.dogrulukLastValue > OyunIslemleri.dogrulukSize) {
            dogrulukDatabase.dogrulukDao().updateAll()
            OyunIslemleri.dogrulukLastValue = 1
        }
        soruGetir(dogrulukMu)
    }

    private fun soruGetir(dogrulukMu: Boolean) {
        if (dogrulukMu) {
            val model = dogrulukDatabase.dogrulukDao().getModel(OyunIslemleri.dogrulukLastValue)
            if (model == null) {
                OyunIslemleri.dogrulukLastValue++
                soruGetir(dogrulukMu)
            } else {
                OyunIslemleri.dogrulukLastValue++
                model.sorulduMu = true
                dogrulukDatabase.dogrulukDao().update(model)
                gosterilenSoru = model.soru
            }

        } else {
            val model = cesaretDatabase.cesaretDao().getModel(OyunIslemleri.cesaretLastValue)
            if (model == null) {
                OyunIslemleri.cesaretLastValue++
                soruGetir(dogrulukMu)
            } else {
                OyunIslemleri.cesaretLastValue++
                model.sorulduMu = true
                cesaretDatabase.cesaretDao().update(model)
                gosterilenSoru = model.soru
            }

        }
    }

    private fun sharedGuncelle() {
        model.updateLastValue(
            OyunIslemleri.dogrulukLastValue,
            OyunIslemleri.cesaretLastValue
        )
    }

}
