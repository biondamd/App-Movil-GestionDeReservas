package com.andesaventura.app.ui.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.andesaventura.app.data.SessionManager
import com.andesaventura.app.databinding.FragmentContactosBinding
import com.andesaventura.app.databinding.FragmentInicioBinding
import com.andesaventura.app.databinding.FragmentLoginBinding
import com.andesaventura.app.databinding.FragmentNosotrosBinding
import com.andesaventura.app.ui.LoginActivity
import com.andesaventura.app.ui.MapaActivity
import com.andesaventura.app.ui.PaquetesActivity
import com.andesaventura.app.ui.ReservasActivity
import com.andesaventura.app.ui.SalariosActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class InicioFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentInicioBinding.inflate(inflater, container, false)
        binding.tvBienvenida.text = "Hola, ${SessionManager(requireContext()).usuario ?: "viajero"}"
        binding.cardPaquetes.setOnClickListener { abrir(PaquetesActivity::class.java) }
        binding.cardReservas.setOnClickListener { abrir(ReservasActivity::class.java) }
        binding.cardMapa.setOnClickListener { abrir(MapaActivity::class.java) }
        binding.cardSalarios.setOnClickListener { abrir(SalariosActivity::class.java) }
        return binding.root
    }

    private fun abrir(destino: Class<*>) = startActivity(Intent(requireContext(), destino))
}

class NosotrosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        FragmentNosotrosBinding.inflate(inflater, container, false).root
}

/** Contactos con formulario de envío de datos. */
class ContactosFragment : Fragment() {
    private var _binding: FragmentContactosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentContactosBinding.inflate(inflater, container, false)
        binding.btnEnviar.setOnClickListener { enviar() }
        return binding.root
    }

    private fun enviar() {
        val nombre = binding.etNombre.text.toString().trim()
        val correo = binding.etCorreo.text.toString().trim()
        val mensaje = binding.etMensaje.text.toString().trim()

        binding.tilNombre.error = if (nombre.length < 3) "Ingrese su nombre" else null
        binding.tilCorreo.error = if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) "Correo no válido" else null
        binding.tilMensaje.error = if (mensaje.length < 10) "Mínimo 10 caracteres" else null
        if (binding.tilNombre.error != null || binding.tilCorreo.error != null || binding.tilMensaje.error != null) return

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Mensaje enviado")
            .setMessage("Gracias $nombre, te responderemos a $correo.\n\nMensaje:\n$mensaje")
            .setPositiveButton("Aceptar", null)
            .show()
        binding.etNombre.text = null
        binding.etCorreo.text = null
        binding.etMensaje.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class LoginFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = FragmentLoginBinding.inflate(inflater, container, false)
        val sesion = SessionManager(requireContext())
        binding.tvUsuario.text = "Sesión iniciada como:\n${sesion.usuario}"
        binding.btnCerrarSesion.setOnClickListener {
            sesion.cerrar()
            startActivity(
                Intent(requireContext(), LoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            )
        }
        return binding.root
    }
}
