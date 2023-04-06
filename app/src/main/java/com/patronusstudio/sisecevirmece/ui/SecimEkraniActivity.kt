package com.patronusstudio.sisecevirmece.ui

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.databinding.ActivitySecimEkraniBinding
import com.patronusstudio.sisecevirmece.enums.DogrulukCesaret
import com.patronusstudio.sisecevirmece.util.extSayfaGecisi
import com.patronusstudio.sisecevirmece.util.extStatusBarColor

class SecimEkraniActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivitySecimEkraniBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_secim_ekrani)

        this extStatusBarColor "#00000000"

        binding.cesaret = DogrulukCesaret.CESARET.isim
        binding.dogruluk = DogrulukCesaret.DOGRULUK.isim

        binding.cardviewGroup.findViewById<ImageView>(R.id.imgCesaret).setOnClickListener {
            it.context.extSayfaGecisi(SoruActivity::class.java)
            finish()
        }

        binding.cardviewGroup.findViewById<ImageView>(R.id.imgDogruluk).setOnClickListener {
            it.context.extSayfaGecisi(
                SoruActivity::class.java,
                DogrulukCesaret.DOGRULUK_CESARET.isim,
                true
            )
            finish()
        }

    }

}
