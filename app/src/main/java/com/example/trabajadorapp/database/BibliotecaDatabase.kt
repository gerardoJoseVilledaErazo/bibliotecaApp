package com.example.trabajadorapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trabajadorapp.Models.Interfaces.LibroDao
import com.example.trabajadorapp.Models.Interfaces.RevistaDao
import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.RevistaEntity

@Database(entities = [LibroEntity::class, RevistaEntity::class], version = 1)
abstract class BibliotecaDatabase : RoomDatabase() {
    abstract fun libroDao(): LibroDao
    abstract fun revistaDao(): RevistaDao
}