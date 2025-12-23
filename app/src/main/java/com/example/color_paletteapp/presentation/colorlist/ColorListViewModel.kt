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

    val savedColors: StateFlow<List<ColorCard>> = repository.getSavedColors()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun deleteColor(colorId: Int) {
        viewModelScope.launch {
            repository.deleteColor(colorId)
        }
    }
}