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
        containerColor = beigeColor
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