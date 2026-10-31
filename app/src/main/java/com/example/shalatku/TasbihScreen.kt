package com.example.shalatku

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TasbihScreen(
    count: Int,
    rounds: Int,
    roundSize: Int,
    onIncrement: () -> Unit,
    onReset: () -> Unit,
    onSelectRoundSize: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Target: $roundSize")
        Text("Putaran: $rounds")
        Text("$count")
        Button(onClick = onIncrement) { Text("Tap") }
        OutlinedButton(onClick = onReset) { Text("Reset") }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            listOf(33, 99, 100).forEach { size ->
                OutlinedButton(onClick = { onSelectRoundSize(size) }) {
                    Text("$size")
                }
            }
        }
    }
}

@Preview
@Composable
fun TasbihScreenPreview() {
    TasbihScreen(
        count = 12, rounds = 2, roundSize = 33,
        onIncrement = {}, onReset = {}, onSelectRoundSize = {}
    )
}