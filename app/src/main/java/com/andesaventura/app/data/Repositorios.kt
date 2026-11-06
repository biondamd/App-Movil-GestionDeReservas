package com.andesaventura.app.data

import com.andesaventura.app.model.EstadoReserva
import com.andesaventura.app.model.Paquete
import com.andesaventura.app.model.Reserva
import com.andesaventura.app.model.Trabajador

object PaqueteRepository {
    private val paquetes = listOf(
        Paquete(1, "Camino Inca", "Machu Picchu, Cusco",
            "Trekking clásico de 4 días por senderos incas hasta la ciudadela de Machu Picchu.",
            "4 días / 3 noches", 1850.0, "Plaza de Armas del Cusco", -13.5167, -71.9781),
        Paquete(2, "Montaña de 7 Colores", "Vinicunca, Cusco",
            "Caminata de alta montaña hasta los 5 200 m.s.n.m. con vistas al nevado Ausangate.",
            "1 día", 180.0, "Plaza San Francisco, Cusco", -13.5186, -71.9806),
        Paquete(3, "Cañón del Colca", "Chivay, Arequipa",
            "Recorrido por uno de los cañones más profundos del mundo y avistamiento de cóndores.",
            "2 días / 1 noche", 420.0, "Plaza de Armas de Arequipa", -16.3989, -71.5370),
        Paquete(4, "Sandboarding Huacachina", "Huacachina, Ica",
            "Tubulares en las dunas del desierto y sandboarding al atardecer en el oasis.",
            "1 día", 150.0, "Plaza de Armas de Ica", -14.0639, -75.7292),
        Paquete(5, "Laguna 69", "Huascarán, Áncash",
            "Caminata en la Cordillera Blanca hasta la laguna turquesa al pie del Chacraraju.",
            "1 día", 120.0, "Plaza de Armas de Huaraz", -9.5277, -77.5287),
        Paquete(6, "Canotaje en Lunahuaná", "Lunahuaná, Lima",
            "Descenso en balsa por los rápidos del río Cañete con guías certificados.",
            "1 día", 95.0, "Plaza de Armas de Lunahuaná", -12.9614, -76.1408)
    )

    fun listar(): List<Paquete> = paquetes
}

object ReservaRepository {
    private val reservas = mutableListOf(
        Reserva(1, "Lucía Quispe", "984123456", "Camino Inca", "15/11/2026", 2,
            EstadoReserva.CONFIRMADO, "Plaza de Armas del Cusco", -13.5167, -71.9781),
        Reserva(2, "Carlos Mendoza", "987654321", "Sandboarding Huacachina", "20/11/2026", 4,
            EstadoReserva.PENDIENTE, "Plaza de Armas de Ica", -14.0639, -75.7292),
        Reserva(3, "Ana Torres", "912345678", "Laguna 69", "02/10/2026", 1,
            EstadoReserva.FINALIZADO, "Plaza de Armas de Huaraz", -9.5277, -77.5287)
    )

    fun listar(): List<Reserva> = reservas.toList()

    fun obtener(id: Int): Reserva? = reservas.find { it.id == id }

    fun agregar(
        clienteNombre: String, telefono: String, paquete: Paquete,
        fechaViaje: String, cantidadPersonas: Int
    ): Reserva {
        val nueva = Reserva(
            id = (reservas.maxOfOrNull { it.id } ?: 0) + 1,
            clienteNombre = clienteNombre,
            telefono = telefono,
            destino = paquete.nombre,
            fechaViaje = fechaViaje,
            cantidadPersonas = cantidadPersonas,
            estadoReserva = EstadoReserva.PENDIENTE,
            puntoEncuentro = paquete.puntoEncuentro,
            latitud = paquete.latitud,
            longitud = paquete.longitud
        )
        reservas.add(nueva)
        return nueva
    }

    fun actualizarEstado(id: Int, estado: EstadoReserva) {
        obtener(id)?.estadoReserva = estado
    }
}

object TrabajadorRepository {
    fun listar() = listOf(
        Trabajador("Miguel Huamán", "Guía de alta montaña", 2800.0, 400.0),
        Trabajador("Rosa Condori", "Guía bilingüe", 2500.0, 300.0),
        Trabajador("Jorge Salas", "Conductor", 1800.0, 150.0),
        Trabajador("Valeria Ríos", "Ejecutiva de reservas", 2200.0, 200.0),
        Trabajador("Diego Paredes", "Administrador", 3500.0, 500.0)
    )
}
