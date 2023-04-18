package com.patronusstudio.sisecevirmece.ui

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.patronusstudio.sisecevirmece.databinding.DialogNewAppScreenBinding
import com.patronusstudio.sisecevirmece.enums.PlayStore

class DialogNewAppFragment: DialogFragment() {

    private lateinit var binding:DialogNewAppScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNewAppScreenBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onStart() {
        super.onStart()
        setWindowParams()
        listener()
    }

    private fun setWindowParams(){
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
    }

    private fun listener(){
        binding.playStoreDownload.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse(PlayStore.SISE_CEVIRMECE_2.isim)
                )
            )
        }
        binding.imgClose.setOnClickListener {
            this.dismiss()
        }
    }
}