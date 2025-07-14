package com.example.dessert_clicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessert_clicker.data.Datasource.dessertList
import com.example.dessert_clicker.data.DessertUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertViewModel : ViewModel() {
    private val _dessertUiState = MutableStateFlow(DessertUiState())
    val dessertUiState: StateFlow<DessertUiState> = _dessertUiState.asStateFlow()

    fun onDessertClicked() {
        _dessertUiState.update { cupcakeUiState ->
            val dessertsSold = cupcakeUiState.dessertsSold + 1
            val nextDessertIndex = determineDessertIndex(dessertsSold)

            val nextStart = if (nextDessertIndex + 1 < dessertList.size){
                dessertList[nextDessertIndex + 1].startProductionAmount
            } else Int.MAX_VALUE

            val currentStart = dessertList[nextDessertIndex].startProductionAmount
            val range = (nextStart - currentStart).coerceAtLeast(1)
            val progress = ((dessertsSold - currentStart).toFloat() / range).coerceIn(0f, 1f)

            cupcakeUiState.copy(
                currentDessertIndex = nextDessertIndex,
                revenue = cupcakeUiState.revenue + cupcakeUiState.currentDessertPrice,
                dessertsSold = dessertsSold,
                currentDessertImageId = dessertList[nextDessertIndex].imageId,
                currentDessertPrice = dessertList[nextDessertIndex].price,
                nextDessertStart = nextStart,
                progressToNext = progress
            )
        }
    }

    fun determineDessertIndex(dessertsSold: Int): Int {
        var dessertIndex = 0
        for (index in dessertList.indices) {
            if (dessertsSold >= dessertList[index].startProductionAmount) {
                dessertIndex = index
            } else {
                break
            }
        }
        return dessertIndex
    }
}