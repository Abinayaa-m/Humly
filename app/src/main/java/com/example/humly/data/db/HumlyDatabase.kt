package com.example.humly.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.humly.data.model.Recording

@Database(entities = [Recording::class], version = 1, exportSchema = false)
abstract class HumlyDatabase : RoomDatabase() {
    abstract fun recordingDao(): RecordingDao

    companion object {
        @Volatile
        private var INSTANCE: HumlyDatabase? = null

        fun getDatabase(context: Context): HumlyDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HumlyDatabase::class.java,
                    "humly_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}