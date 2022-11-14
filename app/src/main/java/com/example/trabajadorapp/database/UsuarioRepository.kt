package com.example.trabajadorapp.database

import com.example.trabajadorapp.Models.Interfaces.UsuarioDao
import com.example.trabajadorapp.Models.UsuarioEntity

class UsuarioRepository(private val dao: UsuarioDao) {
//    val usuarios = dao.getAll()
//
//    suspend fun getUsername(userName: String): UsuarioEntity? = dao.getUsername(userName)

    suspend fun insert(usuario: UsuarioEntity) = dao.addUsuario(usuario)
}