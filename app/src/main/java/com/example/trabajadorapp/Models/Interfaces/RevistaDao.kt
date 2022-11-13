package com.example.trabajadorapp.Models.Interfaces

import androidx.room.*
import com.example.trabajadorapp.Models.RevistaEntity

@Dao
interface RevistaDao {

    @Query("SELECT * FROM RevistaEntity")
    fun getAll(): MutableList<RevistaEntity>

    @Insert
    fun addRevista(revistaEntity: RevistaEntity)

    @Update
    fun updateRevista(revistaEntity: RevistaEntity)

    @Delete
    fun deleteRevista(revistaEntity: RevistaEntity)
}