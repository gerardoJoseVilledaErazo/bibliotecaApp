package com.example.trabajadorapp.Models.Interfaces

import androidx.room.*
import com.example.trabajadorapp.Models.UsuarioEntity

@Dao
interface UsuarioDao {

    @Insert
    fun addUsuario(usuarioEntity: UsuarioEntity)

    @Query("SELECT EXISTS (SELECT * FROM UsuarioEntity WHERE username=:username)")
    fun is_taken(username: String): Boolean

    @Query("SELECT EXISTS (SELECT * FROM UsuarioEntity WHERE username=:username AND password=:password)")
    fun login(username: String, password: String): Boolean

}