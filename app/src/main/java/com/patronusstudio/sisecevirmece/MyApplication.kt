package com.patronusstudio.sisecevirmece

import android.app.Application
import com.applovin.sdk.AppLovinSdk

class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        AppLovinSdk.getInstance(this).initializeSdk()
        AppLovinSdk.getInstance(this).mediationProvider = "max"
    }
}