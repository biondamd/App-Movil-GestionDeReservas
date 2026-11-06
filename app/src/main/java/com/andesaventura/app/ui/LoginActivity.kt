package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import com.andesaventura.app.data.SessionManager
import com.andesaventura.app.databinding.ActivityLoginBinding

class LoginActivity : BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnIngresar.setOnClickListener { validarIngreso() }
    }

    private fun validarIngreso() {
        val correo = binding.etCorreo.text.toString().trim()
        val clave = binding.etPassword.text.toString()
        binding.tilCorreo.error = null
        binding.tilPassword.error = null

        when {
            correo.isEmpty() -> binding.tilCorreo.error = "Ingrese su correo"
            !Patterns.EMAIL_ADDRESS.matcher(correo).matches() -> binding.tilCorreo.error = "Correo no válido"
            clave.length < 6 -> binding.tilPassword.error = "Mínimo 6 caracteres"
            correo != SessionManager.USUARIO_DEMO || clave != SessionManager.PASSWORD_DEMO ->
                binding.tilPassword.error = "Usuario o contraseña incorrectos"
            else -> {
                SessionManager(this).iniciar(correo)
                startActivity(Intent(this, MainActivity::class.java).putExtra(EXTRA_USUARIO, correo))
                finish()
            }
        }
    }
}
