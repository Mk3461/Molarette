/*
package com.example.color_paletteapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.KeyboardActions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
    // topBar = { TopAppBar(title = { Text("Hex Kodu İle Renk Ara") }) }
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Renk Arama") }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // 1. Arama Çubuğu
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Hex Kodu (Örn: FF5733)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ara") },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = { viewModel.searchColor() }
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // Arama Butonu
            Button(
                onClick = { viewModel.searchColor() },
                enabled = !uiState.isLoading && uiState.searchQuery.isNotBlank()
            ) {
                Text(if (uiState.isLoading) "Aranıyor..." else "Ara")
            }

            Spacer(Modifier.height(24.dp))

            // 2. Hata/Uyarı Mesajı
            uiState.errorMessage?.let { error ->
                Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(8.dp))
                Spacer(Modifier.height(16.dp))
            }

            // 3. Sonuç Kartı
            uiState.searchResult?.let { colorCard ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(colorCard.composeColor),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("#${colorCard.hexCode}", style = MaterialTheme.typography.headlineMedium, color = Color.White)
                            Text(colorCard.name, style = MaterialTheme.typography.titleMedium, color = Color.White)
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                // Kaydet Butonu
                Button(
                    onClick = { viewModel.saveSearchedColor() },
                    enabled = !colorCard.isSaved
                ) {
                    Text(if (colorCard.isSaved) "Kaydedildi" else "Kaydet")
                }
            }
        }
    }
}
*/
package com.example.color_paletteapp.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(viewModel: SearchViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val beigeColor = Color(0xFFF5F5DC)

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Renk Ara") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(beigeColor) // 🎨 Arka plan bej
                .verticalScroll(scrollState) // 📜 Kaydırma özelliği
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.updateSearchQuery(it) },
                label = { Text("Hex Kodu (Örn: FF5733)") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Button(onClick = { viewModel.searchColor() }, enabled = !uiState.isLoading) {
                Text(if (uiState.isLoading) "Aranıyor..." else "Ara")
            }

            uiState.searchResult?.let { color ->
                Spacer(Modifier.height(24.dp))
                Card(modifier = Modifier.fillMaxWidth().height(150.dp)) {
                    Box(modifier = Modifier.fillMaxSize().background(color.composeColor), contentAlignment = Alignment.Center) {
                        Text(color.name, color = Color.White, style = MaterialTheme.typography.titleLarge)
                    }
                }
                Button(onClick = { viewModel.saveSearchedColor() }, enabled = !color.isSaved) {
                    Text(if (color.isSaved) "Kaydedildi" else "Kaydet")
                }
            }
        }
    }
}