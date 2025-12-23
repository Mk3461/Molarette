/*
package com.example.color_paletteapp.presentation.colorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.color_paletteapp.domain.model.ColorCard
import androidx.compose.material3.ExperimentalMaterial3Api
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorListScreen(viewModel: ColorListViewModel) {

    // ViewModel'dan gelen renk listesini izler
    val colors by viewModel.savedColors.collectAsState()

    Scaffold(
        //topBar = { TopAppBar(title = { Text("Kaydedilen Renkler (${colors.size})") }) }
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Kaydedilen Renkler (${colors.size})") }
            )
        }
    ) { padding ->

        if (colors.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Henüz kaydedilmiş bir renk yok.")
            }
        } else {
            LazyColumn(
                contentPadding = padding,
                modifier = Modifier.fillMaxSize()
            ) {
                items(colors, key = { it.id ?: it.hexCode }) { color ->
                    ColorListItem(
                        colorCard = color,
                        onDelete = {
                            color.id?.let { id -> viewModel.deleteColor(id) }
                        }
                    )
                    Divider()
                }
            }
        }
    }
}

@Composable
fun ColorListItem(colorCard: ColorCard, onDelete: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Renk örneği
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(colorCard.composeColor, shape = MaterialTheme.shapes.small)
        )

        Spacer(Modifier.width(16.dp))

        // Renk adı ve Hex kodu
        Column(modifier = Modifier.weight(1f)) {
            Text(colorCard.name, style = MaterialTheme.typography.titleMedium)
            Text("#${colorCard.hexCode}", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
        }

        // Silme butonu
        IconButton(onClick = onDelete) {
            Icon(Icons.Default.Delete, contentDescription = "Sil", tint = Color.Red.copy(alpha = 0.7f))
        }
    }
}
*/
package com.example.color_paletteapp.presentation.colorlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorListScreen(viewModel: ColorListViewModel) {
    val colors by viewModel.savedColors.collectAsState()
    val beigeColor = Color(0xFFF5F5DC)

    Scaffold(
        topBar = { CenterAlignedTopAppBar(title = { Text("Kaydedilenler") }) },
        containerColor = beigeColor // 🎨 Scaffold arka planı bej
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize()
        ) {
            items(colors) { color ->
                ListItem(
                    headlineContent = { Text(color.name) },
                    supportingContent = { Text("#${color.hexCode}") },
                    leadingContent = {
                        Box(Modifier.size(40.dp).background(color.composeColor, MaterialTheme.shapes.small))
                    },
                    trailingContent = {
                        IconButton(onClick = { color.id?.let { viewModel.deleteColor(it) } }) {
                            Icon(Icons.Default.Delete, contentDescription = null, tint = Color.Red)
                        }
                    },
                    colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                )
                HorizontalDivider()
            }
        }
    }
}