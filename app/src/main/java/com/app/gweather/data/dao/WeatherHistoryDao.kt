package com.app.gweather.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.app.gweather.data.local.WeatherHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherHistoryDao {

    /**
     * Insert a history entry. If an entry with the same id exists, replace it.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: WeatherHistoryEntity)

    /**
     * Return all history entries ordered by timestamp descending (most recent first).
     * This is a suspend function returning a snapshot list (matches the repository usage above).
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    suspend fun getAll(): List<WeatherHistoryEntity>

    /**
     * Flow variant: emits updates when the table changes. Useful for UI that reacts to live updates.
     */
    @Query("SELECT * FROM weather_history ORDER BY timestamp DESC")
    fun getAllFlow(): Flow<List<WeatherHistoryEntity>>

    /** Delete all history entries (useful for testing or a 'clear history' feature). */
    @Query("DELETE FROM weather_history")
    suspend fun deleteAll()

    /** Count entries in history. */
    @Query("SELECT COUNT(*) FROM weather_history")
    suspend fun count(): Int
}
