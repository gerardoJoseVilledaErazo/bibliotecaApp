package com.example.trabajadorapp

import android.app.Application
import androidx.room.Room
import com.example.trabajadorapp.database.BibliotecaDatabase

class BibliotecaApplication : Application() {

    companion object {
        lateinit var database: BibliotecaDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            BibliotecaDatabase::class.java,
            "BibliotecaDB"
        )
            .build()
    }
}