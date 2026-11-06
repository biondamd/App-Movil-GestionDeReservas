package com.andesaventura.app.ui

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import com.andesaventura.app.data.TrabajadorRepository
import com.andesaventura.app.databinding.ActivitySalariosBinding
import com.andesaventura.app.model.Trabajador

/** Formulario para consultar el salario de los trabajadores. */
class SalariosActivity : BaseActivity() {

    private lateinit var binding: ActivitySalariosBinding
    private val trabajadores = TrabajadorRepository.listar()
    private var elegido: Trabajador? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySalariosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, "Salarios")

        binding.actvTrabajador.setAdapter(ArrayAdapter(this, android.R.layout.simple_list_item_1, trabajadores))
        binding.actvTrabajador.setOnItemClickListener { _, _, posicion, _ ->
            elegido = trabajadores[posicion]
            binding.tilTrabajador.error = null
        }
        binding.btnCalcular.setOnClickListener { calcular() }
    }

    private fun calcular() {
        val horas = binding.etHorasExtra.text.toString().ifEmpty { "0" }.toIntOrNull()
        binding.tilTrabajador.error = if (elegido == null) "Seleccione un trabajador" else null
        binding.tilHorasExtra.error = if (horas == null || horas !in 0..60) "Entre 0 y 60 horas" else null
        val t = elegido
        if (t == null || binding.tilHorasExtra.error != null) return

        val h = horas!!
        binding.tvResNombre.text = t.nombre
        binding.tvResCargo.text = "Cargo: ${t.cargo}"
        binding.tvResDetalle.text = """
            Sueldo base: S/ %.2f
            Bonificación: S/ %.2f
            Horas extra ($h): S/ %.2f
            Sueldo bruto: S/ %.2f
            AFP (13%%): - S/ %.2f
        """.trimIndent().format(
            t.sueldoBase, t.bonificacion, h * Trabajador.PAGO_HORA_EXTRA,
            t.sueldoBruto(h), t.descuentoAfp(h)
        )
        binding.tvResNeto.text = "Sueldo neto: S/ %.2f".format(t.sueldoNeto(h))
        binding.cardResultado.visibility = View.VISIBLE
    }
}
