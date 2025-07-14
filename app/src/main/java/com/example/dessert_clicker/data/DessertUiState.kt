package com.example.dessert_clicker.data

import androidx.annotation.DrawableRes
import com.example.dessert_clicker.data.Datasource.dessertList

data class DessertUiState(
    val currentDessertIndex: Int = 0,
    val dessertsSold: Int = 0,
    val revenue: Int = 0,
    val currentDessertPrice: Int = dessertList[currentDessertIndex].price,
    @DrawableRes val currentDessertImageId: Int = dessertList[currentDessertIndex].imageId,
    val nextDessertStart: Int = getNextDessertStart(currentDessertIndex),
    val progressToNext: Float = 0f // 0.0 - 1.0
)

private fun getNextDessertStart(currentIndex: Int): Int {
    return if (currentIndex + 1 < dessertList.size) {
        dessertList[currentIndex + 1].startProductionAmount
    } else Int.MAX_VALUE
}