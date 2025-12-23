package com.example.color_paletteapp.presentation.randomcolor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.color_paletteapp.domain.model.ColorCard
import com.example.color_paletteapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RandomColorViewModel(
    private val repository: ColorRepository // Repository enjeksiyon ile gelir
) : ViewModel() {

    // UI Durumu: Rastgele üretilen renk kartı (Nullable olabilir)
    private val _randomColor = MutableStateFlow<ColorCard?>(null)
    val randomColor: StateFlow<ColorCard?> = _randomColor

    // UI Durumu: Yükleniyor bilgisi
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        // ViewModel başlatıldığında hemen bir renk çek
        generateNewRandomColor()
    }

    /**
     * API'den rastgele bir renk çeker ve durumu günceller.
     */
    fun generateNewRandomColor() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newColor = repository.fetchRandomColor()
                _randomColor.value = newColor
            } catch (e: Exception) {
                // Hata yönetimi (loglama, kullanıcıya mesaj gösterme vb.)
                println("Rastgele renk çekme hatası: ${e.message}")
                _randomColor.value = null // Hata durumunda rengi sıfırla
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Şu anda ekranda gösterilen rengi veritabanına kaydeder.
     */
    fun saveCurrentColor() {
        _randomColor.value?.let { color ->
            viewModelScope.launch {
                // Rengi kaydederken isSaved bayrağını true yaparak kaydederiz.
                repository.saveColor(color.copy(isSaved = true))

                // UI'daki durumu anında güncelle
                _randomColor.value = color.copy(isSaved = true)
            }
        }
    }
}