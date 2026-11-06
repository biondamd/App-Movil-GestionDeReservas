package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import com.andesaventura.app.adapter.ReservaAdapter
import com.andesaventura.app.data.ReservaRepository
import com.andesaventura.app.databinding.ActivityReservaDetalleBinding
import com.andesaventura.app.model.EstadoReserva
import com.andesaventura.app.model.Reserva
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ReservaDetalleActivity : BaseActivity() {

    private lateinit var binding: ActivityReservaDetalleBinding
    private var reservaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservaDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, "Detalle de reserva")

        reservaId = intent.getIntExtra(EXTRA_RESERVA_ID, 0)
        val reserva = ReservaRepository.obtener(reservaId)
        if (reserva == null) {
            finish()
            return
        }
        mostrar(reserva)

        binding.btnCambiarEstado.setOnClickListener { elegirEstado() }
        binding.btnVerMapa.setOnClickListener {
            startActivity(
                Intent(this, MapaActivity::class.java)
                    .putExtra(EXTRA_TITULO, reserva.puntoEncuentro)
                    .putExtra(EXTRA_LATITUD, reserva.latitud)
                    .putExtra(EXTRA_LONGITUD, reserva.longitud)
            )
        }
    }

    private fun mostrar(reserva: Reserva) {
        binding.tvCodigo.text = "Reserva N° ${reserva.id}"
        binding.tvCliente.text = "Cliente: ${reserva.clienteNombre}"
        binding.tvTelefono.text = "Teléfono: ${reserva.telefono}"
        binding.tvDestino.text = "Destino: ${reserva.destino}"
        binding.tvFecha.text = "Fecha de viaje: ${reserva.fechaViaje}"
        binding.tvPersonas.text = "Personas: ${reserva.cantidadPersonas}"
        binding.tvPuntoEncuentro.text = "Punto de encuentro: ${reserva.puntoEncuentro}"
        ReservaAdapter.pintarEstado(binding.chipEstado, reserva.estadoReserva)
    }

    private fun elegirEstado() {
        val estados = EstadoReserva.values()
        MaterialAlertDialogBuilder(this)
            .setTitle("Cambiar estado")
            .setItems(estados.map { it.name }.toTypedArray()) { _, i ->
                ReservaRepository.actualizarEstado(reservaId, estados[i])
                ReservaRepository.obtener(reservaId)?.let { mostrar(it) }
            }
            .show()
    }
}
