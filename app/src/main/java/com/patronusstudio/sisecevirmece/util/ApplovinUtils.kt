package com.patronusstudio.sisecevirmece.util

import android.view.ViewGroup
import android.widget.FrameLayout
import com.applovin.mediation.ads.MaxAdView
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.interfaces.AdviewListener

object ApplovinUtils {
    fun createBanner(viewGroup: ViewGroup, adUnitId:String){
        val adView = MaxAdView(adUnitId, viewGroup.context)
        adView.setListener(AdviewListener())
        // Stretch to the width of the screen for banners to be fully functional
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        // Banner height on phones and tablets is 50 and 90, respectively
        val heightPx = viewGroup.context.resources.getDimensionPixelSize(R.dimen.banner_height)
        adView.layoutParams = FrameLayout.LayoutParams(width, heightPx)
        viewGroup.addView(adView)
        // Load the ad
        adView.loadAd()
    }
}