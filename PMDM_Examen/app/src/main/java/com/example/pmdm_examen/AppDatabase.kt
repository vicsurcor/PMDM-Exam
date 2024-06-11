package com.example.pmdm_examen

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Car::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "car_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

