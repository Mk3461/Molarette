package com.example.color_paletteapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_colors")
data class ColorEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val hexCode: String,
    val userDefinedName: String? = null,
    val timestamp: Long = 0
)