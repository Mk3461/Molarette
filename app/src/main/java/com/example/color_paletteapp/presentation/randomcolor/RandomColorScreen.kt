/*
package com.example.color_paletteapp.presentation.randomcolor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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

    // 🛑 HAYATİ IMPORTLAR: StateFlow'dan değerleri izliyoruz
    // Bu satırlar, daha önceki "Unresolved reference" hatalarını çözen kısımlardır.
    val colorCard by viewModel.randomColor.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Eğer renk kaydedilmişse, butonun rengini değiştirmek için
    val isColorSaved = colorCard?.isSaved ?: false

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // 1. Renk Kartı Gösterimi
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.5f), // 1.5'e 1 oranında dikdörtgen kart
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    // ViewModel'dan gelen rengi kullan, yoksa açık gri göster
                    .background(colorCard?.composeColor ?: Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text(
                        text = colorCard?.hexCode ?: "Renk Yok",
                        style = MaterialTheme.typography.headlineMedium,
                        // Arka plan rengine göre metin rengini ayarlama mantığı eklenebilir.
                        color = Color.White
                    )
                }
            }
        }

        Spacer(Modifier.height(32.dp))

        // 2. Butonlar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Yeni Üret Butonu
            Button(
                onClick = { viewModel.generateNewRandomColor() },
                enabled = !isLoading
            ) {
                Text("Yeni Renk Üret")
            }

            // Kaydet Butonu
            Button(
                onClick = { viewModel.saveCurrentColor() },
                // Yükleme yoksa ve renk zaten kaydedilmemişse etkinleştir
                enabled = !isLoading && colorCard != null && !isColorSaved,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isColorSaved) Color(0xFF4CAF50) else MaterialTheme.colorScheme.primary // Yeşil renk
                )
            ) {
                Text(if (isColorSaved) "KAYDEDİLDİ" else "Kaydet")
            }
        }

        // Kaydedildi Mesajı (Geri Bildirim)
        if (isColorSaved && !isLoading) {
            Spacer(Modifier.height(16.dp))
            Text(
                text = "${colorCard?.name ?: "Renk"} başarıyla kaydedildi.",
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFF4CAF50)
            )
        }
    }
}
*/
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

    // Bej rengi tanımı
    val beigeColor = Color(0xFFF5F5DC)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(beigeColor) // 🎨 Arka plan bej
            .verticalScroll(scrollState) // 📜 Kaydırma özelliği
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