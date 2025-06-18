package com.example.humly.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.humly.data.model.Recording
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordingDao {
    @Insert
    suspend fun insert(recording: Recording)

    @Query("SELECT * FROM recordings WHERE userId = :userId ORDER BY timestamp DESC")
    fun getRecordingsByUser(userId: String): Flow<List<Recording>>

    @Query("DELETE FROM recordings WHERE id = :id")
    suspend fun delete(id: Int)
}