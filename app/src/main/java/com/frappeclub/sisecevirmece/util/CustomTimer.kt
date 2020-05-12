package com.frappeclub.sisecevirmece.util

import android.os.CountDownTimer
import com.frappeclub.sisecevirmece.enums.GameTimer

class CustomTimer {

    private var ikiSaniye = object : CountDownTimer(
        GameTimer.TWO_SECOND.getTimer(),
        GameTimer.ONE_SECOND.getTimer()
    ) {
        override fun onFinish() {

        }

        override fun onTick(millisUntilFinished: Long) {
        }

    }

    fun sayacBaslat() {
        ikiSaniye.start()
    }

    fun sayacDurdur() {
        ikiSaniye.cancel()
    }

}