package com.example.color_paletteapp.data.local.dao

import androidx.room.*
import com.example.color_paletteapp.data.local.entity.ColorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ColorDao {

    @Query("SELECT * FROM saved_colors ORDER BY timestamp DESC")
    fun getAllColors(): Flow<List<ColorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertColor(color: ColorEntity)

    @Query("DELETE FROM saved_colors WHERE id = :colorId")
    suspend fun deleteColor(colorId: Int)
}