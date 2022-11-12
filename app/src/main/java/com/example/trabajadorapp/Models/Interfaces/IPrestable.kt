package com.example.trabajadorapp.Models.Interfaces

interface IPrestable {
    fun prestar()
    fun devolver()
    fun Prestado(): Boolean
}