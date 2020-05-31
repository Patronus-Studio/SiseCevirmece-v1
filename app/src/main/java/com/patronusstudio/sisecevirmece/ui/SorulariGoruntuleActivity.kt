package com.patronusstudio.sisecevirmece.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.florent37.viewtooltip.ViewTooltip
import com.patronusstudio.sisecevirmece.R
import com.patronusstudio.sisecevirmece.abstracts.CesaretDatabase
import com.patronusstudio.sisecevirmece.abstracts.DogrulukDatabase
import com.patronusstudio.sisecevirmece.adapter.SorularAdapter
import com.patronusstudio.sisecevirmece.binding.SorulariGoruntuleOnClickBinding
import com.patronusstudio.sisecevirmece.databinding.ActivitySorulariGoruntuleBinding
import com.patronusstudio.sisecevirmece.enums.DogrulukCesaret
import com.patronusstudio.sisecevirmece.model.CesaretModel
import com.patronusstudio.sisecevirmece.model.DogrulukModel
import com.patronusstudio.sisecevirmece.util.SharedVeriSaklama


class SorulariGoruntuleActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySorulariGoruntuleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sorulari_goruntule)

        val getBooleanIntent = intent.getBooleanExtra(DogrulukCesaret.DOGRULUK_CESARET.isim, false)
        val list =
            if (getBooleanIntent)
                DogrulukDatabase.getDatabaseManager(this).dogrulukDao().getAllModel()
            else CesaretDatabase.getDatabaseManager(this).cesaretDao().getAllModel()

        val longClick = { position: Int ->

            val model = if (getBooleanIntent) list[position] as DogrulukModel
            else list[position] as CesaretModel

            val gecis = Intent(applicationContext, SoruDuzenleActivitiy::class.java)
            gecis.putExtra(DogrulukCesaret.DOGRULUK_CESARET.isim, getBooleanIntent)
            gecis.putExtra(DogrulukCesaret.SORU_MODELI.isim, model)
            startActivity(gecis)

        }

        binding.dogrulukMu = getBooleanIntent
        binding.liste = list
        binding.onClickBinding = SorulariGoruntuleOnClickBinding(this)
        binding.sorularRecycler.adapter = SorularAdapter(list, getBooleanIntent, longClick)
        binding.sorularRecycler.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        toolTip()
    }

    private fun toolTip() {

        val sharedPref = SharedVeriSaklama(this)
        val tooltipGosterildiMi = sharedPref.getToolTip()

        if (!tooltipGosterildiMi) {
            ViewTooltip
                .on(this, binding.sorularRecycler)
                .autoHide(true, 10000)
                .corner(30)
                .position(ViewTooltip.Position.BOTTOM)
                .text(R.string.silmek_icin_uzun_bas)
                .textColor(Color.BLACK)
                .color(Color.GREEN)
                .clickToHide(true)
                .textSize(1, 16f)
                .arrowWidth(16)
                .arrowHeight(16)
                .show()
            sharedPref.updateToolTip(true)
        }


    }

}