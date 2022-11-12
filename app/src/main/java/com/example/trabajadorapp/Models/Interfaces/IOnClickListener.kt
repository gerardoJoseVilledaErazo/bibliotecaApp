package com.example.trabajadorapp.Models.Interfaces

import com.example.trabajadorapp.Models.Libro

interface IOnClickListener {
    fun onClickListener(libro: Libro, position: Int)
}