package com.example.shalatku

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ShalatkuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ShalatKuUiState())
    val uiState: StateFlow<ShalatKuUiState> = _uiState.asStateFlow()

    // Bukan UI state: tidak ada layar yang membacanya.
    private var loadCalls = 0

    fun togglePrayer(name: String) {
        _uiState.update { state ->
            val done = if (name in state.donePrayers) {
                state.donePrayers - name
            } else {
                state.donePrayers + name
            }
            state.copy(donePrayers = done)
        }
    }

    fun clearPrayers() {
        _uiState.update { it.copy(donePrayers = emptyList()) }
    }

    fun selectDay(index: Int) {
        _uiState.update { it.copy(selectedDay = index) }
    }

    fun incrementTasbih() {
        _uiState.update { state ->
            val next = state.tasbihCount + 1
            if (next == state.roundSize) {
                state.copy(tasbihCount = 0, completedRounds = state.completedRounds + 1)
            } else {
                state.copy(tasbihCount = next)
            }
        }
    }

    fun resetTasbih() {
        _uiState.update { it.copy(tasbihCount = 0, completedRounds = 0) }
    }

    fun setRoundSize(size: Int) {
        // Target baru: count direset, putaran tetap.
        _uiState.update { it.copy(roundSize = size, tasbihCount = 0) }
    }

    fun loadPrayerTimes() {
        if (_uiState.value.prayerTimes is PrayerTimesUiState.Loading) return

        loadCalls++
        val shouldFail = loadCalls % 2 == 0   // panggilan 1 sukses, 2 gagal, 3 sukses, ...

        _uiState.update { it.copy(prayerTimes = PrayerTimesUiState.Loading) }

        viewModelScope.launch {
            delay(1500)
            _uiState.update {
                it.copy(
                    prayerTimes = if (shouldFail) {
                        PrayerTimesUiState.Error("Failed to load prayer times. Please try again.")
                    } else {
                        PrayerTimesUiState.Success(samplePrayers)
                    }
                )
            }
        }
    }
}