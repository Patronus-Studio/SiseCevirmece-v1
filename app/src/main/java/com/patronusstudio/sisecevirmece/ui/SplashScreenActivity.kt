package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.app.lets_go_splash.CreateAnim
import com.app.lets_go_splash.OnAnimationListener
import com.app.lets_go_splash.StarterAnimation
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.abstracts.CesaretDatabase
import com.patronusstudio.sisecevirmece.abstracts.DogrulukDatabase
import com.patronusstudio.sisecevirmece.util.DrinkUtils
import com.patronusstudio.sisecevirmece.util.OyunIslemleri
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import com.patronusstudio.sisecevirmece.util.SoruEkleme
import com.patronusstudio.sisecevirmece.util.extSayfaGecisi
import com.patronusstudio.sisecevirmece.util.extStatusBarColor


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        this extStatusBarColor "#00000000"

        val sharedVeriSaklama = SharedVeriSaklama(this)
        val isWrited = sharedVeriSaklama.isSharedPrefCreated()

        if (!isWrited) {
            val soruEkleme = SoruEkleme(this)
            val cesaretDatabase = CesaretDatabase.getDatabaseManager(this)
            cesaretDatabase.cesaretDao().insertAll(soruEkleme.cesaretListesiEkleme())

            val dogrulukDatabase = DogrulukDatabase.getDatabaseManager(this)
            dogrulukDatabase.dogrulukDao().insertAll(soruEkleme.dogrulukListesiEkleme())

            sharedVeriSaklama.putValueForFirstStarted(
                true, soruEkleme.dogrulukListSize, soruEkleme.cesaretListSize, 4
            )
        }

        OyunIslemleri.cesaretSize = sharedVeriSaklama.getCesaretListValue()
        OyunIslemleri.dogrulukSize = sharedVeriSaklama.getDogrulukListValue()

        OyunIslemleri.cesaretLastValue = sharedVeriSaklama.getCesaretLastValue()
        OyunIslemleri.dogrulukLastValue = sharedVeriSaklama.getDogrulukLastValue()

        DrinkUtils().setSelectedDrinks(this, sharedVeriSaklama.getSiseTuru())
        startAnim()


    }

    private fun startAnim() {
        StarterAnimation(resList = getAnimList(),
            onAnimationListener = object : OnAnimationListener {
                override fun onRepeat() {}
                override fun onEnd() {
                    findViewById<ImageView>(R.id.splashScreenImg).visibility = View.GONE
                    this@SplashScreenActivity.extSayfaGecisi(HomeActivity::class.java)
                    finish()
                }

                override fun onStartAnim() {
                }
            }).startSequentialAnimation(view = findViewById<ImageView>(R.id.splashScreenImg))

    }

    private fun getAnimList(): ArrayList<Animation> {
        val animList: ArrayList<Animation> = ArrayList()

        animList.add(CreateAnim.createAnimation(applicationContext, R.anim.no_animation))
        animList.add(CreateAnim.createAnimation(applicationContext, R.anim.rotate))
        animList.add(CreateAnim.createAnimation(applicationContext, R.anim.zoom_out_fast))
        animList.add(CreateAnim.createAnimation(applicationContext, R.anim.fade_in))

        return animList
    }


}

