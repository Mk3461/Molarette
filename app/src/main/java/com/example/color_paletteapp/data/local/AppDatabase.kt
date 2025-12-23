package com.example.color_paletteapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.color_paletteapp.data.local.dao.ColorDao
import com.example.color_paletteapp.data.local.entity.ColorEntity

@Database(
    entities = [ColorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun colorDao(): ColorDao
}