package com.example.busschedule.database.schedule

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Entity
data class Schedule(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "stop_name") val stopName: String,
    @ColumnInfo(name = "arrival_time") val arrivalTime: Int,
)
// @NotNull is equivalent to not having '?' in property

@Dao
interface ScheduleDao {
    @Query("select * from schedule order by arrival_time")
    fun getAll(): Flow<List<Schedule>> // For screen 1

    @Query("select * from schedule where stop_name = :stopName order by arrival_time")
    fun getByStopName(stopName: String): Flow<List<Schedule>>
}