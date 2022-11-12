package com.example.trabajadorapp.Repository

import com.example.trabajadorapp.Models.Publicacion

data class PublicacionRepository(var lstPublicaciones: MutableList<Publicacion> = mutableListOf()) {
    fun add(publicacion: Publicacion) {
        lstPublicaciones.add(publicacion)
    }

    fun get(): MutableList<Publicacion> = lstPublicaciones
}