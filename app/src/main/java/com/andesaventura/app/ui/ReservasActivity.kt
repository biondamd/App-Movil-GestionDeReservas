package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andesaventura.app.adapter.ReservaAdapter
import com.andesaventura.app.data.ReservaRepository
import com.andesaventura.app.databinding.ActivityReservasBinding

class ReservasActivity : BaseActivity() {

    private lateinit var binding: ActivityReservasBinding
    private val adapter = ReservaAdapter { reserva ->
        startActivity(Intent(this, ReservaDetalleActivity::class.java).putExtra(EXTRA_RESERVA_ID, reserva.id))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReservasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, "Reservas")

        binding.rvReservas.layoutManager = LinearLayoutManager(this)
        binding.rvReservas.adapter = adapter
        binding.fabNuevaReserva.setOnClickListener {
            startActivity(Intent(this, NuevaReservaActivity::class.java))
        }
    }

    /** Se recarga al volver de registrar o editar una reserva. */
    override fun onResume() {
        super.onResume()
        adapter.actualizar(ReservaRepository.listar())
    }
}
