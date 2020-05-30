package com.frappeclub.sisecevirmece.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.frappeclub.sisecevirmece.R
import com.frappeclub.sisecevirmece.abstracts.CesaretDatabase
import com.frappeclub.sisecevirmece.abstracts.DogrulukDatabase
import com.frappeclub.sisecevirmece.adapter.SorularAdapter
import com.frappeclub.sisecevirmece.binding.SorulariGoruntuleOnClickBinding
import com.frappeclub.sisecevirmece.databinding.ActivitySorulariGoruntuleBinding
import com.frappeclub.sisecevirmece.enums.DogrulukCesaret
import com.frappeclub.sisecevirmece.model.CesaretModel
import com.frappeclub.sisecevirmece.model.DogrulukModel

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
    }
}