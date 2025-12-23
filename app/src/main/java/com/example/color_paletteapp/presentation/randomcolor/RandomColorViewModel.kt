package com.example.color_paletteapp.presentation.randomcolor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.color_paletteapp.domain.model.ColorCard
import com.example.color_paletteapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomColorViewModel(
    private val repository: ColorRepository
) : ViewModel() {

    private val _randomColor = MutableStateFlow<ColorCard?>(null)
    val randomColor: StateFlow<ColorCard?> = _randomColor


    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        generateNewRandomColor()
    }

    fun generateNewRandomColor() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newColor = repository.fetchRandomColor()
                _randomColor.value = newColor
            } catch (e: Exception) {

                println("Rastgele renk çekme hatası: ${e.message}")
                _randomColor.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveCurrentColor() {
        _randomColor.value?.let { color ->
            viewModelScope.launch {
                repository.saveColor(color.copy(isSaved = true))


                _randomColor.value = color.copy(isSaved = true)
            }
        }
    }
}