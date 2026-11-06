package com.andesaventura.app.ui

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.andesaventura.app.adapter.PaqueteAdapter
import com.andesaventura.app.data.PaqueteRepository
import com.andesaventura.app.databinding.ActivityPaquetesBinding

class PaquetesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPaquetesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, "Paquetes turísticos")

        binding.rvPaquetes.layoutManager = LinearLayoutManager(this)
        binding.rvPaquetes.adapter = PaqueteAdapter(PaqueteRepository.listar()) { paquete ->
            startActivity(Intent(this, PaqueteDetalleActivity::class.java).putExtra(EXTRA_PAQUETE, paquete))
        }
    }
}
