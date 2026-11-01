package com.example.shalatku

data class ShalatKuUiState(
    val prayers: List<Prayer> = samplePrayers,
    val donePrayers: List<String> = emptyList(),
    val selectedDay: Int = 0,
    val tasbihCount: Int = 0,
    val completedRounds: Int = 0,
    val roundSize: Int = 33,
    val prayerTimes: PrayerTimesUiState = PrayerTimesUiState.Success(samplePrayers),
) {
    val doneCount: Int
        get() = donePrayers.size
}