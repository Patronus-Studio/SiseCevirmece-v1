package com.patronusstudio.sisecevirmece.util

import android.content.Context
import com.patronusstudio.sisecevirmece.model.DrinkType

class DrinkUtils {
    fun compareAndTransform(drinks: List<DrinkType>, itemId: Int): List<DrinkType> {
        val copiedList = mutableListOf<DrinkType>()
        drinks.forEach {
            val copied = if (it.id == itemId) {
                it.copy(isSelected = true)
            } else it.copy(isSelected = false)
            copiedList.add(copied)
        }
        return copiedList
    }

    fun getSelectedSiseTuru(): DrinkType {
        return try {
            OyunIslemleri.drinks.first {
                it.isSelected
            }
        } catch (e: Exception) {
            OyunIslemleri.drinks.first()
        }
    }

    fun setSelectedDrinks(context:Context,id: Int) {
        OyunIslemleri.drinks.forEach {
            it.isSelected = it.id == id
        }
        SharedVeriSaklama(context).updateSiseValue(id)
    }
}
