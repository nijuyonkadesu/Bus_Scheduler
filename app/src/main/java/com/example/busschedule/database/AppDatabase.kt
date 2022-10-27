package com.example.busschedule.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.busschedule.database.schedule.Schedule
import com.example.busschedule.database.schedule.ScheduleDao

// Single stop to access all DAO classes
@Database(entities = [Schedule::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun scheduleDao(): ScheduleDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "app_database"
                    )
                    .createFromAsset("database/bus_schedule.db") // pre-population
                    .build()
                INSTANCE = instance

                instance
            }
        }
    }
}
/*
Why to tell ROOM bout subclasses ?

1) Specify what entities are defined in database
2) Provide access to single instance of DAO classes
3) Additional setups / pre-populating

*/