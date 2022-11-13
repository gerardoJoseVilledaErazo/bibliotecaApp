package com.example.trabajadorapp

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.trabajadorapp.MainActivity.Companion.publicacionRepository
import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.RevistaEntity
import com.example.trabajadorapp.databinding.ActivityRegistrarPublicacionBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.regex.Pattern

class RegistrarPublicacionActivity : AppCompatActivity(), View.OnClickListener {

    // Variable para gestionar el viewBinding
    private lateinit var binding: ActivityRegistrarPublicacionBinding
    private var tipoPublicacion: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Configuracion de viewBinding
        binding = ActivityRegistrarPublicacionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // Configuracion del action bar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Titulo para la actividad
        title = "Registrar Publicacion"

        tipoPublicacion = intent.extras!!.getInt("tipoPublicacion")

        // Configuracion de evento click para el botón de registro
        binding.layoutRegistrarPublicacion.btnGuardarRegistro.setOnClickListener(this)

        if (tipoPublicacion == 1) {
            // En este caso el tipo publicacion es libro
            // El formulario de registro debe ocultar el campo numero revista
            binding.layoutRegistrarPublicacion.tilNumeroRevista.visibility = View.GONE
        }
    }

//    override fun onClick(p0: View?) {
//        when (p0!!.id) {
//            binding.layoutRegistrarPublicacion.btnGuardarRegistro.id -> {
//                // Guardar la publicacion
//                // Hay que evaluar si el tipo publicacion es Libro o Revista
//                if (tipoPublicacion == 1) {
//                    if (verifyEmpty(binding.layoutRegistrarPublicacion.edtCodigo) &&
//                        verifyEmpty(binding.layoutRegistrarPublicacion.edtTitulo) &&
//                        verifyEmpty(binding.layoutRegistrarPublicacion.edtAnioPublicacion)
//                    ) {
//                        publicacionRepository.add(
//                            LibroEntity(
//                                binding.layoutRegistrarPublicacion.edtCodigo.text.toString()
//                                    .toInt(),
//                                binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
//                                binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString()
//                                    .toInt()
//                            )
//                        )
//                        // Llamado al dialog
//                        configProgressDialog()
//
//                    }
//                } else if (tipoPublicacion == 2) {
//
//                    if (verifyEmpty(binding.layoutRegistrarPublicacion.edtCodigo) &&
//                        verifyEmpty(binding.layoutRegistrarPublicacion.edtTitulo) &&
//                        verifyEmpty(binding.layoutRegistrarPublicacion.edtAnioPublicacion) &&
//                        verifyEmpty(binding.layoutRegistrarPublicacion.edtNumeroRevista)
//                    ) {
//                        publicacionRepository.add(
//                            RevistaEntity(
//                                binding.layoutRegistrarPublicacion.edtCodigo.text.toString()
//                                    .toInt(),
//                                binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
//                                binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString()
//                                    .toInt(),
//                                binding.layoutRegistrarPublicacion.edtNumeroRevista.text.toString()
//                                    .toInt()
//                            )
//                        )
//                        // Llamado al dialog
//                        configProgressDialog()
//
//                    }
//                }
//            }
//        }
//    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            binding.layoutRegistrarPublicacion.btnGuardarRegistro.id -> {
                // Guardar la publicacion
                // Hay que evaluar si el tipo publicacion es LibroEntity o RevistaEntity
                if (tipoPublicacion == 1) {
                    if (verifyEmpty(binding.layoutRegistrarPublicacion.edtCodigo) &&
                        verifyEmpty(binding.layoutRegistrarPublicacion.edtTitulo) &&
                        verifyEmpty(binding.layoutRegistrarPublicacion.edtAnioPublicacion)
                    ) {
                        doAsync {
                            BibliotecaApplication.database.libroDao().addLibro(
                                LibroEntity(
                                    codigo = binding.layoutRegistrarPublicacion.edtCodigo.text.toString()
                                        .toInt(),
                                    titulo = binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                                    anioPublicacion = binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString()
                                        .toInt()
                                )
                            )
                            uiThread {
//                                finish()
                                goToMainActivity()
                            }
                        }
                        // Llamado al dialog
                        configProgressDialog()
                    }
                } else if (tipoPublicacion == 2) {
//
                    if (verifyEmpty(binding.layoutRegistrarPublicacion.edtCodigo) &&
                        verifyEmpty(binding.layoutRegistrarPublicacion.edtTitulo) &&
                        verifyEmpty(binding.layoutRegistrarPublicacion.edtAnioPublicacion) &&
                        verifyEmpty(binding.layoutRegistrarPublicacion.edtNumeroRevista)
                    ) {
                        doAsync {
                            BibliotecaApplication.database.revistaDao().addRevista(
                                RevistaEntity(
                                    codigo = binding.layoutRegistrarPublicacion.edtCodigo.text.toString()
                                        .toInt(),
                                    titulo = binding.layoutRegistrarPublicacion.edtTitulo.text.toString(),
                                    anioPublicacion = binding.layoutRegistrarPublicacion.edtAnioPublicacion.text.toString()
                                        .toInt(),
                                    numeroRev = binding.layoutRegistrarPublicacion.edtNumeroRevista.text.toString()
                                        .toInt()
                                )
                            )
                            uiThread {
//                                finish()
                                goToMainActivity()
                            }
                        }
                        // Llamado al dialog
                        configProgressDialog()
                    }
                }
            }
        }
    }

    private fun goToMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    // Configuracion del action bar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Deberá permitir volver a la actividad anterior
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Configurador del progress dialog
    fun configProgressDialog() {
        val alertBuilder: AlertDialog.Builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(
            R.layout.progress_dialog,
            null
        )
        alertBuilder.setView(dialogView)
        alertBuilder.setCancelable(false)
        val dialog = alertBuilder.create()
        dialog.show()
        // Configurando hilo, para asignar tiempo
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
            finish()
        }, 3000)
    }


    private fun verifyTextPersonName(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Required field"
            editText.requestFocus()
            return false
        } else if (!verifyChars(editText)) {
            editText.error = "Just letters are allowed"
            editText.requestFocus()
            return false
        }
        return true
    }

    fun verifyChars(editText: EditText): Boolean {
        //Validamos solo caracteres Expresion regular
//        Pattern.compile("^[a-zA-Z ]+$").matcher(editText.text.toString()).matches()

        return Pattern.compile("^[a-zA-Z ]+$").matcher(editText.text.toString()).matches()
    }

    fun verifyEmpty(editText: EditText): Boolean {
        if (editText.text.toString().isEmpty()) {
            editText.error = "Required field"
            editText.requestFocus()
            return false
        }
        return true
    }
}

