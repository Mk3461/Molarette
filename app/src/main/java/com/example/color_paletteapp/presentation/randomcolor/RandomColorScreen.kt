package com.example.color_paletteapp.presentation.randomcolor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RandomColorScreen(viewModel: RandomColorViewModel) {
    val colorCard by viewModel.randomColor.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val scrollState = rememberScrollState()

    val beigeColor = Color(0xFFF5F5DC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(beigeColor)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.2f),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorCard?.composeColor ?: Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = colorCard?.hexCode ?: "#FFFFFF",
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.generateNewRandomColor() }, enabled = !isLoading) {
                Text("Yeni Üret")
            }

            Button(
                onClick = { viewModel.saveCurrentColor() },
                enabled = !isLoading && colorCard != null && colorCard?.isSaved == false,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (colorCard?.isSaved == true) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(if (colorCard?.isSaved == true) "Kaydedildi" else "Kaydet")
            }
        }
    }
}