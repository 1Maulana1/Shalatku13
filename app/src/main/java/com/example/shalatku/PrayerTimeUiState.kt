package com.example.shalatku

sealed interface PrayerTimesUiState {
    data object Loading : PrayerTimesUiState
    data class Success(val times: List<Prayer>) : PrayerTimesUiState
    data class Error(val message: String) : PrayerTimesUiState
}