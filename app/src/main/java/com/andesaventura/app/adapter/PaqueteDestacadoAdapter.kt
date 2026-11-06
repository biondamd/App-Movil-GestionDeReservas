package com.andesaventura.app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.andesaventura.app.databinding.ItemPaqueteDestacadoBinding
import com.andesaventura.app.model.Paquete
import java.util.Locale

/** Tarjeta horizontal de un paquete destacado en la pantalla de inicio. */
class PaqueteDestacadoAdapter(
    private val paquetes: List<Paquete>,
    private val onClick: (Paquete) -> Unit
) : RecyclerView.Adapter<PaqueteDestacadoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPaqueteDestacadoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPaqueteDestacadoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val paquete = paquetes[position]
        with(holder.binding) {
            tvNombre.text = paquete.nombre
            tvDestino.text = paquete.destino
            tvDuracion.text = paquete.duracion
            tvPrecio.text = String.format(Locale.US, "S/ %.2f", paquete.precio)
            root.setOnClickListener { onClick(paquete) }
        }
    }

    override fun getItemCount() = paquetes.size
}