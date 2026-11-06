package com.andesaventura.app.adapter

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.andesaventura.app.R
import com.andesaventura.app.databinding.ItemPaqueteBinding
import com.andesaventura.app.databinding.ItemReservaBinding
import com.andesaventura.app.model.EstadoReserva
import com.andesaventura.app.model.Paquete
import com.andesaventura.app.model.Reserva
import com.google.android.material.chip.Chip

class PaqueteAdapter(
    private val paquetes: List<Paquete>,
    private val alPulsar: (Paquete) -> Unit
) : RecyclerView.Adapter<PaqueteAdapter.Holder>() {

    class Holder(val binding: ItemPaqueteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemPaqueteBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = paquetes.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val p = paquetes[position]
        holder.binding.tvNombre.text = p.nombre
        holder.binding.tvDestino.text = p.destino
        holder.binding.tvPrecio.text = "S/ %.2f · ${p.duracion}".format(p.precio)
        holder.binding.root.setOnClickListener { alPulsar(p) }
    }
}

class ReservaAdapter(
    private val alPulsar: (Reserva) -> Unit
) : RecyclerView.Adapter<ReservaAdapter.Holder>() {

    private var reservas: List<Reserva> = emptyList()

    class Holder(val binding: ItemReservaBinding) : RecyclerView.ViewHolder(binding.root)

    fun actualizar(nuevas: List<Reserva>) {
        reservas = nuevas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        Holder(ItemReservaBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount() = reservas.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val r = reservas[position]
        holder.binding.tvCliente.text = r.clienteNombre
        holder.binding.tvDestino.text = "${r.destino} · ${r.fechaViaje}"
        holder.binding.tvPersonas.text = "${r.cantidadPersonas} persona(s)"
        pintarEstado(holder.binding.chipEstado, r.estadoReserva)
        holder.binding.root.setOnClickListener { alPulsar(r) }
    }

    companion object {
        fun pintarEstado(chip: Chip, estado: EstadoReserva) {
            val color = when (estado) {
                EstadoReserva.PENDIENTE -> R.color.estado_pendiente
                EstadoReserva.CONFIRMADO -> R.color.estado_confirmado
                EstadoReserva.FINALIZADO -> R.color.estado_finalizado
            }
            chip.text = estado.name
            chip.chipBackgroundColor = ColorStateList.valueOf(ContextCompat.getColor(chip.context, color))
            chip.setTextColor(ContextCompat.getColor(chip.context, R.color.blanco))
        }
    }
}
