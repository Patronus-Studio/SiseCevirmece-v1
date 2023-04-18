package com.patronusstudio.sisecevirmece.ui

import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.common.util.SharedPreferencesUtils
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.databinding.ActivityHomeBinding
import com.patronusstudio.sisecevirmece.enums.PlayStore
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama
import com.patronusstudio.sisecevirmece.util.extSayfaGecisi


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

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
            intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(PlayStore.SISE_CEVIRMECE_1.isim))
            startActivity(intent)
        }
        binding.imgApps.setOnClickListener {
            intent =
                Intent(Intent.ACTION_VIEW, Uri.parse(PlayStore.TUM_UYGULAMALAR.isim))
            startActivity(intent)
        }
        sharedPrefControl()
    }


    private fun sharedPrefControl(){
        newAppDialog()

        /*val sharedPref = SharedVeriSaklama(this)
        val isShowed = sharedPref.getBottleFlip2Dialog()
        if(isShowed.not()) {
            newAppDialog()
            sharedPref.putBottleFlip2Dialog()
        }*/
    }
    private fun newAppDialog() {
        val dialog = DialogNewAppFragment()
        dialog.show(supportFragmentManager,"")
    }

}
