package com.example.trabajadorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import com.example.trabajadorapp.databinding.ActivityRegistrarUsuarioBinding
import java.util.regex.Pattern

class RegistrarUsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrarUsuarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_registrar_usuario)
        binding = ActivityRegistrarUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Se agrega la flecha para salir de la actividad
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        // Se configura el nombre de la actividad
        title = "Registrar Usuario"

        binding.btnSave.setOnClickListener {
            if (/*binding.edtUserName.text.toString().isNotEmpty() &&*/
                verifyTextPersonName(binding.edtUserName) &&
//                binding.edtPassword.text.toString().isNotEmpty() &&
                verifyEmpty(binding.edtPassword)

            ) {
                configSharedPreference()
                finish()
            }
//            else {
//                binding.edtUserName.error = "Campo requerido"
//                binding.edtPassword.error = "Campo requerido"
//            }
        }
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