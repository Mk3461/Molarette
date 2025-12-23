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
                .background(beigeColor)
                .verticalScroll(scrollState)
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