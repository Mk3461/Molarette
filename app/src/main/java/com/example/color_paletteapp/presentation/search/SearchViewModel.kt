package com.example.color_paletteapp.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.color_paletteapp.domain.model.ColorCard
import com.example.color_paletteapp.domain.repository.ColorRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// UI durumunu yönetmek için yardımcı data sınıfı
data class SearchUiState(
    val searchQuery: String = "",
    val searchResult: ColorCard? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

class SearchViewModel(
    private val repository: ColorRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState: StateFlow<SearchUiState> = _uiState

    /**
     * Arama metnini günceller.
     */
    fun updateSearchQuery(newQuery: String) {
        _uiState.update {
            it.copy(searchQuery = newQuery, errorMessage = null)
        }
    }

    /**
     * Hex koduna göre API'de arama yapar.
     */
    fun searchColor() {
        val query = _uiState.value.searchQuery.trim()

        if (query.isEmpty() || query.length < 3) {
            _uiState.update {
                it.copy(errorMessage = "Lütfen geçerli bir Hex kodu girin.", searchResult = null)
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                // Sadece Hex kodlarını aramak için # işaretini kaldırırız
                val result = repository.searchColorApi(query.removePrefix("#"))

                if (result != null) {
                    _uiState.update { it.copy(searchResult = result, isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(
                            searchResult = null,
                            isLoading = false,
                            errorMessage = "Renk bulunamadı: #${query}"
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        searchResult = null,
                        isLoading = false,
                        errorMessage = "Ağ hatası oluştu."
                    )
                }
            }
        }
    }

    /**
     * Bulunan rengi kaydeder.
     */
    fun saveSearchedColor() {
        _uiState.value.searchResult?.let { color ->
            viewModelScope.launch {
                repository.saveColor(color)
                _uiState.update { it.copy(searchResult = color.copy(isSaved = true)) }
            }
        }
    }
}