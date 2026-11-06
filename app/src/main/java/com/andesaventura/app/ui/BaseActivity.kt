package com.andesaventura.app.ui

import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
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
        ajustarBordes(toolbar)
    }

    /** Deja espacio para la barra de estado (arriba) y la de navegación (abajo). */
    private fun ajustarBordes(toolbar: MaterialToolbar) {
        val tv = TypedValue()
        theme.resolveAttribute(androidx.appcompat.R.attr.actionBarSize, tv, true)
        val alturaBase = TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(android.R.id.content)) { contenido, insets ->
            val barras = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
            )
            toolbar.updatePadding(top = barras.top)
            toolbar.updateLayoutParams { height = alturaBase + barras.top }
            contenido.updatePadding(left = barras.left, right = barras.right, bottom = barras.bottom)
            insets
        }
    }

    /** Para pantallas sin toolbar (login): evita que la barra de estado y el teclado tapen el contenido. */
    protected fun ajustarBordesLogin(raiz: View) {
        ViewCompat.setOnApplyWindowInsetsListener(raiz) { v, insets ->
            val b = insets.getInsets(
                WindowInsetsCompat.Type.systemBars() or
                    WindowInsetsCompat.Type.displayCutout() or
                    WindowInsetsCompat.Type.ime()
            )
            v.updatePadding(left = b.left, top = b.top, right = b.right, bottom = b.bottom)
            insets
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