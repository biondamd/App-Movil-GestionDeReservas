package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.andesaventura.app.data.SessionManager
import com.andesaventura.app.databinding.ActivitySplashBinding

/** Pantalla de bienvenida que se muestra 5 segundos. */
class SplashActivity : BaseActivity() {

    private val handler = Handler(Looper.getMainLooper())
    private val irSiguiente = Runnable {
        val sesion = SessionManager(this)
        val destino = if (sesion.haySesion) MainActivity::class.java else LoginActivity::class.java
        startActivity(Intent(this, destino))
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivitySplashBinding.inflate(layoutInflater).root)
        handler.postDelayed(irSiguiente, DURACION_MS)
    }

    override fun onDestroy() {
        handler.removeCallbacks(irSiguiente)
        super.onDestroy()
    }

    companion object {
        private const val DURACION_MS = 5000L
    }
}
