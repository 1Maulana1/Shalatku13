package com.example.shalatku

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.shalatku.ui.theme.ShalatkuTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ShalatkuTheme {
                ShalatkuApp()
            }
        }
    }
}

@Composable
fun ShalatkuApp(viewModel: ShalatkuViewModel = viewModel()) {    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // State khusus UI: tab yang aktif.
    var selected by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selected == 0,
                    onClick = { selected = 0 },
                    icon = { Icon(Icons.Default.Home, contentDescription = null) },
                    label = { Text("Home") }
                )
                NavigationBarItem(
                    selected = selected == 1,
                    onClick = { selected = 1 },
                    icon = { Icon(Icons.Default.CheckCircle, contentDescription = null) },
                    label = { Text("Tracker") }
                )
                NavigationBarItem(
                    selected = selected == 2,
                    onClick = { selected = 2 },
                    icon = { Icon(Icons.Default.Add, contentDescription = null) },
                    label = { Text("Tasbih") }
                )
            }
        }
    ) { padding ->
        val contentModifier = Modifier.padding(padding)
        when (selected) {
            0 -> HomeScreen(
                prayerTimes = uiState.prayerTimes,
                onRefreshPrayerTimes = viewModel::loadPrayerTimes,
                modifier = contentModifier
            )
            1 -> TrackerScreen(
                prayers = uiState.prayers,
                donePrayers = uiState.donePrayers,
                doneCount = uiState.doneCount,
                onToggle = viewModel::togglePrayer,
                onClear = viewModel::clearPrayers,
                modifier = contentModifier
            )
            2 -> TasbihScreen(
                count = uiState.tasbihCount,
                rounds = uiState.completedRounds,
                roundSize = uiState.roundSize,
                onIncrement = viewModel::incrementTasbih,
                onReset = viewModel::resetTasbih,
                onSelectRoundSize = viewModel::setRoundSize,
                modifier = contentModifier
            )
        }
    }
}