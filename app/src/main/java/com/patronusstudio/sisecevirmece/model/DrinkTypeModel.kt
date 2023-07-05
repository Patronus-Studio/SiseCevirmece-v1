package com.patronusstudio.sisecevirmece.model

data class DrinkType(
    val id: Int, val name: String, val imageId: Int, var isSelected: Boolean
)

fun List<DrinkType>.compareAndTransform(itemId: Int): List<DrinkType> {
    val drinks = mutableListOf<DrinkType>()
    this.forEach {
        val copied = if (it.id == itemId) {
            it.copy(isSelected = true)
        } else it.copy(isSelected = false)
        drinks.add(copied)
    }
    return drinks
}