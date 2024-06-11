package com.example.pmdm_examen

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CarDao {
    @Insert
    suspend fun insert(car: Car)

    @Update
    suspend fun update(car: Car)

    @Query("SELECT * FROM cars")
    suspend fun getAll(): List<Car>

    @Query("DELETE FROM cars WHERE id = :carId")
    suspend fun deleteById(carId: Int)
}

