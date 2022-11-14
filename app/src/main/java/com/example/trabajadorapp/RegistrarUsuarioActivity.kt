package com.example.trabajadorapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.trabajadorapp.Models.LibroEntity
import com.example.trabajadorapp.Models.RevistaEntity
import com.example.trabajadorapp.Models.UsuarioEntity
import com.example.trabajadorapp.databinding.ActivityRegistrarUsuarioBinding
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.regex.Pattern

class RegistrarUsuarioActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        var isAllowed: Boolean = false
    }

    private lateinit var binding: ActivityRegistrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Se agrega la flecha para salir de la actividad
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Se configura el nombre de la actividad
        title = "Registrar Usuario"

        binding.edtUserName.addTextChangedListener { it: Editable? ->
            val userName: String = it.toString()
            if (/*false*/ BibliotecaApplication.database.getUsuarioDao().is_taken(userName)) {
                isAllowed = false
                Toast.makeText(applicationContext, "Already Taken", Toast.LENGTH_SHORT)
                    .show()
            } else {
                isAllowed = true
            }
        }

        // Configuracion de evento click para el botón de registro
        binding.btnSave.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            binding.btnSave.id -> {
                if (isAllowed) {
                    if (verifyEmpty(binding.edtUserName) && verifyEmpty(binding.edtPassword)) {
                        doAsync {
                            BibliotecaApplication.database.getUsuarioDao().addUsuario(
                                UsuarioEntity(
                                    username = binding.edtUserName.text.toString(),
                                    password = binding.edtPassword.text.toString()
                                )
                            )
                            uiThread {
                                goToLoginActivity()
                            }
                        }
                        // Llamado al dialog
                        configProgressDialog()
                        Toast.makeText(this, "Registro exitoso!", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Username Already Taken", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goToLoginActivity() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun configSharedPreference() {
        val preferences = getSharedPreferences("Users", MODE_PRIVATE)
        with(preferences.edit()) {
            putString("username", binding.edtUserName.text.toString())
            putString("password", binding.edtPassword.text.toString())
                .apply()
        }
    }

    // Se configura la flecha para salir de la actividad
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Finalizar la actividad
                finish()
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