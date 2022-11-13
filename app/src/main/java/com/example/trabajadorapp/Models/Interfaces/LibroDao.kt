package com.example.trabajadorapp.Models.Interfaces

import androidx.room.*
import com.example.trabajadorapp.Models.LibroEntity

@Dao
interface LibroDao {

    @Query("SELECT * FROM LibroEntity")
    fun getAll(): MutableList<LibroEntity>

    @Insert
    fun addLibro(libroEntity: LibroEntity)

    @Update
    fun updateLibro(libroEntity: LibroEntity)

    @Delete
    fun deleteLibro(libroEntity: LibroEntity)
}