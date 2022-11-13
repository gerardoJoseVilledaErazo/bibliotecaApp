package com.example.trabajadorapp.Models.Interfaces

import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.RevistaEntity

interface IOnClickListener {
    fun onClickListener(libro: LibroEntity, position: Int)
    fun onDeleteLibro(libroEntity: LibroEntity, position: Int)
    fun onDeleteRevista(revistaEntity: RevistaEntity, position: Int)
}