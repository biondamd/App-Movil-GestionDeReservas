package com.andesaventura.app.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

/** Activity base: registra el ciclo de vida y configura la Toolbar. */
abstract class BaseActivity : AppCompatActivity() {

    private val tag get() = "CicloVida"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(tag, "${javaClass.simpleName} -> onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d(tag, "${javaClass.simpleName} -> onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(tag, "${javaClass.simpleName} -> onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(tag, "${javaClass.simpleName} -> onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(tag, "${javaClass.simpleName} -> onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(tag, "${javaClass.simpleName} -> onDestroy")
    }

    protected fun configurarToolbar(toolbar: MaterialToolbar, titulo: String, conRetroceso: Boolean = true) {
        setSupportActionBar(toolbar)
        supportActionBar?.title = titulo
        if (conRetroceso) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolbar.setNavigationOnClickListener { onBackPressedDispatcher.onBackPressed() }
        }
    }

    companion object {
        const val EXTRA_USUARIO = "extra_usuario"
        const val EXTRA_PAQUETE = "extra_paquete"
        const val EXTRA_RESERVA_ID = "extra_reserva_id"
        const val EXTRA_LATITUD = "extra_latitud"
        const val EXTRA_LONGITUD = "extra_longitud"
        const val EXTRA_TITULO = "extra_titulo"
    }
}
