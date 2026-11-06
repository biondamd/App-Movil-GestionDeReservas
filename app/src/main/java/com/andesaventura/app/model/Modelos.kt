package com.andesaventura.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

enum class EstadoReserva { PENDIENTE, CONFIRMADO, FINALIZADO }

/** Paquete turístico ofrecido por la empresa. */
@Parcelize
data class Paquete(
    val id: Int,
    val nombre: String,
    val destino: String,
    val descripcion: String,
    val duracion: String,
    val precio: Double,
    val puntoEncuentro: String,
    val latitud: Double,
    val longitud: Double
) : Parcelable

/** Reserva turística con los datos mínimos solicitados. */
data class Reserva(
    val id: Int,
    val clienteNombre: String,
    val telefono: String,
    val destino: String,
    val fechaViaje: String,
    val cantidadPersonas: Int,
    var estadoReserva: EstadoReserva,
    val puntoEncuentro: String,
    val latitud: Double,
    val longitud: Double
)

/** Trabajador de la empresa y cálculo de su salario. */
data class Trabajador(
    val nombre: String,
    val cargo: String,
    val sueldoBase: Double,
    val bonificacion: Double
) {
    fun sueldoBruto(horasExtra: Int): Double = sueldoBase + bonificacion + horasExtra * PAGO_HORA_EXTRA
    fun descuentoAfp(horasExtra: Int): Double = sueldoBruto(horasExtra) * TASA_AFP
    fun sueldoNeto(horasExtra: Int): Double = sueldoBruto(horasExtra) - descuentoAfp(horasExtra)

    override fun toString() = nombre

    companion object {
        const val TASA_AFP = 0.13
        const val PAGO_HORA_EXTRA = 15.0
    }
}
