package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.core.content.IntentCompat
import com.andesaventura.app.databinding.ActivityPaqueteDetalleBinding
import com.andesaventura.app.model.Paquete

class PaqueteDetalleActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaqueteDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val paquete = IntentCompat.getParcelableExtra(intent, EXTRA_PAQUETE, Paquete::class.java)
        if (paquete == null) {
            finish()
            return
        }
        configurarToolbar(binding.toolbar, paquete.nombre)

        binding.tvNombre.text = paquete.nombre
        binding.tvDestino.text = paquete.destino
        binding.tvDescripcion.text = paquete.descripcion
        binding.tvDuracion.text = "Duración: ${paquete.duracion}"
        binding.tvPrecio.text = "Precio: S/ %.2f por persona".format(paquete.precio)
        binding.tvPuntoEncuentro.text = "Punto de encuentro: ${paquete.puntoEncuentro}"

        binding.btnVerMapa.setOnClickListener {
            startActivity(
                Intent(this, MapaActivity::class.java)
                    .putExtra(EXTRA_TITULO, paquete.puntoEncuentro)
                    .putExtra(EXTRA_LATITUD, paquete.latitud)
                    .putExtra(EXTRA_LONGITUD, paquete.longitud)
            )
        }
        binding.fabReservar.setOnClickListener {
            startActivity(Intent(this, NuevaReservaActivity::class.java).putExtra(EXTRA_PAQUETE, paquete))
        }
    }
}
