package com.example.trabajadorapp

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.trabajadorapp.Repository.PublicacionRepository
import com.example.trabajadorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    // Variable para manejar el viewBinding
    private lateinit var binding: ActivityMainBinding

    // Configuracion de companion object para agregar registros a la lista desde otras pantallas
    companion object {
        val publicacionRepository: PublicacionRepository =
            PublicacionRepository()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        // Configuración de ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        configuracionBotones()
    }

    private fun configuracionBotones() {
        // Configurar evento click de los botones
        binding.layoutPrincipal.btnAgregarPub.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarLista.setOnClickListener(this)
        binding.layoutPrincipal.btnMostrarDatos.setOnClickListener(this)
        binding.layoutPrincipal.btnCerrarSesion.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            binding.layoutPrincipal.btnAgregarPub.id -> {
                // Abrir pantalla seleccionar publicacion
                startActivity(Intent(this, SeleccionarPublicacionActivity::class.java))
            }
            binding.layoutPrincipal.btnMostrarLista.id -> {
                // Mostrar lista de publicaciones
                startActivity(Intent(this, TipoListaActivity::class.java))
//                startActivity(Intent(this, MostrarListaActivity::class.java))
            }
            binding.layoutPrincipal.btnMostrarDatos.id -> {
                // Mostrar datos del desarrollador
//                // Permite ir a la vista para mostrar los datos del desarrollador de la APP
                startActivity(Intent(this, MostrarDatosActivity::class.java))
            }
            binding.layoutPrincipal.btnCerrarSesion.id -> {
//                onBackPressed()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
    }
}