package com.example.trabajadorapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.trabajadorapp.Models.Interfaces.UsuarioDao
import com.example.trabajadorapp.Models.UsuarioEntity

@Database(entities = [UsuarioEntity::class], version = 1)
abstract class UsuarioDatabase : RoomDatabase() {

    abstract fun getUsuarioDao(): UsuarioDao
}