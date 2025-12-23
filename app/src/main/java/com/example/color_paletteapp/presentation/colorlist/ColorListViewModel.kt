package com.example.color_paletteapp.presentation.colorlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.color_paletteapp.domain.model.ColorCard
import com.example.color_paletteapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ColorListViewModel(
    private val repository: ColorRepository
) : ViewModel() {

    // 1. Kaydedilen renkleri çeker ve StateFlow olarak sunar.
    val savedColors: StateFlow<List<ColorCard>> = repository.getSavedColors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000), // Uygulama aktifken veriyi dinler
            initialValue = emptyList()
        )

    /**
     * Listedeki bir rengi veritabanından siler.
     */
    fun deleteColor(colorId: Int) {
        viewModelScope.launch {
            repository.deleteColor(colorId)
        }
    }
}