package com.patronusstudio.sisecevirmece.binding

import android.app.Activity
import android.view.View
import android.widget.RadioButton
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.util.ApplovinUtils
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama

class SiseSecimiOnClickBinding(private val mainView: View, private val activity: Activity) {

    private val model by lazy {
        SharedVeriSaklama(mainView.context)
    }

    init {
        val sise = OyunIslemleri.siseTuru
        setRadioButton(sise)
    }

    fun imageOnClick(view: View, siseTuru: Int) {
        var isShowed = false
        ApplovinUtils.createInterstitialAd(activity,
            activity.getString(R.string.interstitial_bottle_select_ad_id),
            onAdClosed = {
                setRadioButton(siseTuru)
                kayit(islemTuru = siseTuru)
                isShowed = false
            }, onAdLoaded = {
                if(isShowed.not() && it.isReady){
                    it.showAd()
                    isShowed = true
                }

            })
    }

    private fun setRadioButton(siseTuru: Int) {
        when (siseTuru) {
            1 -> {
                mainView.findViewById<RadioButton>(R.id.radioCola).isChecked = true
            }

            2 -> {
                mainView.findViewById<RadioButton>(R.id.radioWine).isChecked = true
            }

            3 -> {
                mainView.findViewById<RadioButton>(R.id.radioWhisky).isChecked = true
            }

            4 -> {
                mainView.findViewById<RadioButton>(R.id.radioBottle).isChecked = true
            }
        }
    }


    private fun kayit(isFirstCall: Boolean = false, islemTuru: Int = 0) {

        if (isFirstCall) {
            val sharedSiseTuru = model.getSiseTuru()
            setRadioButton(sharedSiseTuru)
            OyunIslemleri.siseTuru = sharedSiseTuru
        } else {
            OyunIslemleri.siseTuru = islemTuru
            model.updateSiseValue(islemTuru)
        }

    }


}