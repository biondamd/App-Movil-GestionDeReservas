package com.andesaventura.app.data

import android.content.Context

/** Guarda la sesión del usuario en SharedPreferences. */
class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    val usuario: String? get() = prefs.getString(KEY_USUARIO, null)
    val haySesion: Boolean get() = usuario != null

    fun iniciar(usuario: String) = prefs.edit().putString(KEY_USUARIO, usuario).apply()
    fun cerrar() = prefs.edit().remove(KEY_USUARIO).apply()

    companion object {
        private const val KEY_USUARIO = "usuario"
        const val USUARIO_DEMO = "admin@andesaventura.pe"
        const val PASSWORD_DEMO = "123456"
    }
}
