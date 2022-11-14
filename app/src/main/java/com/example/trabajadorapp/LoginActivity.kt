package com.example.trabajadorapp

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.trabajadorapp.databinding.ActivityLoginBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.regex.Pattern

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = "Iniciar Sesión"

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(this, RegistrarUsuarioActivity::class.java))
        }

        binding.btnSave.setOnClickListener {
            login()
        }

        configGlide()
    }

    private fun configGlide() {
        val url =
            "https://yt3.ggpht.com/ytc/AMLnZu9-hbX2bZLgbBKPQ9P1sSg9U0wL44dmcHLcSX5BvQ=s900-c-k-c0x00ffffff-no-rj"
        Glide.with(this)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .circleCrop()
            .into(binding.imgvIconApp)
    }

    private fun login() {
        if (verifyEmpty(binding.edtUserName) && verifyEmpty(binding.edtPassword)) {
            val userName: String = binding.edtUserName.text.toString()
            val password: String = binding.edtPassword.text.toString()
            if (BibliotecaApplication.database.getUsuarioDao().login(userName, password) /*true*/) {
                startActivity(Intent(this, MainActivity::class.java))
                Toast.makeText(
                    this, "Bienvenido $userName",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Credenciales incorrectas...")
                    .setMessage("Usuario/Contraseña no son correctos o no esta registrado, registrese primero")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        }
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