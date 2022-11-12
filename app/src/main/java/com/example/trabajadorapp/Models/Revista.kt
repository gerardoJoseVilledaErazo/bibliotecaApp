package com.example.trabajadorapp.Models

class Revista(
    private var c: Int,
    private var t: String,
    private var a: Int,
    private var numeroRev: Int
) : Publicacion(c, t, a) {
    /// getNumeroRevista
    fun getNumRev(): Int = this.numeroRev
    override fun getTipoPublicacion(): Int = 2
}