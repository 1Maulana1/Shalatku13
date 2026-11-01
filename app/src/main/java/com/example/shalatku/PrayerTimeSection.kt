package com.example.shalatku

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PrayerTimesSection(
    state: PrayerTimesUiState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier.fillMaxWidth()) {
        when (state) {
            PrayerTimesUiState.Loading -> {
                Box(
                    Modifier.fillMaxWidth().padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is PrayerTimesUiState.Success -> {
                state.times.forEach { prayer -> PrayerCard(prayer) }
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRefresh) { Text("Refresh") }
            }
            is PrayerTimesUiState.Error -> {
                Text(
                    state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(8.dp))
                Button(onClick = onRefresh) { Text("Retry") }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PrayerTimesLoadingPreview() {
    PrayerTimesSection(state = PrayerTimesUiState.Loading, onRefresh = {})
}

@Preview(showBackground = true)
@Composable
fun PrayerTimesSuccessPreview() {
    PrayerTimesSection(
        state = PrayerTimesUiState.Success(samplePrayers),
        onRefresh = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PrayerTimesErrorPreview() {
    PrayerTimesSection(
        state = PrayerTimesUiState.Error("Failed to load prayer times."),
        onRefresh = {}
    )
}