package com.andesaventura.app.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.IntentCompat
import com.andesaventura.app.data.PaqueteRepository
import com.andesaventura.app.data.ReservaRepository
import com.andesaventura.app.databinding.ActivityNuevaReservaBinding
import com.andesaventura.app.model.Paquete
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.TimeZone

/** Registro de cliente y reserva con validaciones visuales. */
class NuevaReservaActivity : BaseActivity() {

    private lateinit var binding: ActivityNuevaReservaBinding
    private val paquetes = PaqueteRepository.listar()
    private var paqueteElegido: Paquete? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNuevaReservaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, "Nueva reserva")

        binding.actvDestino.setAdapter(
            ArrayAdapter(this, android.R.layout.simple_list_item_1, paquetes.map { it.nombre })
        )
        binding.actvDestino.setOnItemClickListener { _, _, posicion, _ ->
            paqueteElegido = paquetes[posicion]
            binding.tilDestino.error = null
        }
        IntentCompat.getParcelableExtra(intent, EXTRA_PAQUETE, Paquete::class.java)?.let {
            paqueteElegido = it
            binding.actvDestino.setText(it.nombre, false)
        }

        binding.etFecha.setOnClickListener { elegirFecha() }
        binding.btnGuardar.setOnClickListener { guardar() }
    }

    private fun elegirFecha() {
        val picker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Fecha de viaje")
            .setCalendarConstraints(
                CalendarConstraints.Builder().setValidator(DateValidatorPointForward.now()).build()
            )
            .build()
        picker.addOnPositiveButtonClickListener { millis ->
            val formato = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            formato.timeZone = TimeZone.getTimeZone("UTC")
            binding.etFecha.setText(formato.format(millis))
            binding.tilFecha.error = null
        }
        picker.show(supportFragmentManager, "fecha")
    }

    private fun guardar() {
        val nombre = binding.etCliente.text.toString().trim()
        val telefono = binding.etTelefono.text.toString().trim()
        val fecha = binding.etFecha.text.toString()
        val personas = binding.etPersonas.text.toString().toIntOrNull()

        binding.tilCliente.error = if (nombre.length < 3) "Ingrese el nombre del cliente" else null
        binding.tilTelefono.error = if (!telefono.matches(Regex("9\\d{8}"))) "Celular de 9 dígitos que empiece en 9" else null
        binding.tilDestino.error = if (paqueteElegido == null) "Seleccione un destino" else null
        binding.tilFecha.error = if (fecha.isEmpty()) "Seleccione la fecha" else null
        binding.tilPersonas.error = if (personas == null || personas !in 1..20) "Entre 1 y 20 personas" else null

        val valido = listOf(binding.tilCliente, binding.tilTelefono, binding.tilDestino, binding.tilFecha, binding.tilPersonas)
            .all { it.error == null }
        if (!valido) return

        val reserva = ReservaRepository.agregar(nombre, telefono, paqueteElegido!!, fecha, personas!!)
        Toast.makeText(this, "Reserva N° ${reserva.id} registrada", Toast.LENGTH_LONG).show()
        finish()
    }
}
