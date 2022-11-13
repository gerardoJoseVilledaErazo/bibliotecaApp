package com.example.trabajadorapp.Models

abstract class Publicacion(
//    protected var codigo: Int,
//    protected var titulo: String,
//    protected var anioPublicacion: Int
    open var id: Long = 0,
    open var codigo:Int,
    open var titulo: String,
    open var anioPublicacion: Int
) {
    fun getCode(): Int = this.codigo
    fun getTitle(): String = this.titulo
    fun getYearPub(): Int = this.anioPublicacion
    abstract fun getTipoPublicacion(): Int
}