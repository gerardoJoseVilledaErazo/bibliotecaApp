package com.example.trabajadorapp.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "RevistaEntity")
class RevistaEntity(
    @PrimaryKey(autoGenerate = true)
    override var id: Long = 0,
    override var codigo: Int,
    override var titulo: String,
    override var anioPublicacion: Int,
    @ColumnInfo(name = "numeroRevista")
    var numeroRev: Int
) : Publicacion(codigo = codigo, titulo = titulo, anioPublicacion = anioPublicacion) {
    //    private var c: Int,
//    private var t: String,
//    private var a: Int,
//    private var numeroRev: Int
//) : Publicacion(c, t, a) {

    /// getNumeroRevista
    fun getNumRev(): Int = this.numeroRev
    override fun getTipoPublicacion(): Int = 2
}