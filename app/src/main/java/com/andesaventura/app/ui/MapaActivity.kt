package com.andesaventura.app.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.andesaventura.app.R
import com.andesaventura.app.data.PaqueteRepository
import com.andesaventura.app.databinding.ActivityMapaBinding
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/** Muestra puntos de encuentro y la ubicación actual (real o simulada). */
class MapaActivity : BaseActivity() {

    private lateinit var binding: ActivityMapaBinding
    private var mapa: GoogleMap? = null
    private var destino: LatLng? = null

    private val permisoLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { resultado ->
        if (resultado.values.any { it }) {
            obtenerUbicacion()
        } else {
            Toast.makeText(this, "Permiso denegado: se usa ubicación simulada", Toast.LENGTH_LONG).show()
            mostrarUbicacion(UBICACION_SIMULADA, simulada = true)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configurarToolbar(binding.toolbar, intent.getStringExtra(EXTRA_TITULO) ?: "Puntos de encuentro")

        if (intent.hasExtra(EXTRA_LATITUD)) {
            destino = LatLng(intent.getDoubleExtra(EXTRA_LATITUD, 0.0), intent.getDoubleExtra(EXTRA_LONGITUD, 0.0))
        }

        val fragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        fragment.getMapAsync { googleMap ->
            mapa = googleMap
            googleMap.uiSettings.isZoomControlsEnabled = true
            dibujarPuntos()
        }

        binding.fabMiUbicacion.setOnClickListener { solicitarUbicacion() }
    }

    private fun dibujarPuntos() {
        val googleMap = mapa ?: return
        PaqueteRepository.listar().forEach {
            googleMap.addMarker(
                MarkerOptions().position(LatLng(it.latitud, it.longitud))
                    .title(it.puntoEncuentro).snippet(it.nombre)
            )
        }
        val centro = destino ?: LatLng(-12.5, -74.0)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centro, if (destino != null) 14f else 5f))
    }

    private fun solicitarUbicacion() {
        val concedido = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED
        if (concedido) {
            obtenerUbicacion()
        } else {
            permisoLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    @SuppressLint("MissingPermission")
    private fun obtenerUbicacion() {
        mapa?.isMyLocationEnabled = true
        LocationServices.getFusedLocationProviderClient(this)
            .getCurrentLocation(Priority.PRIORITY_BALANCED_POWER_ACCURACY, null)
            .addOnSuccessListener { loc ->
                if (loc != null) mostrarUbicacion(LatLng(loc.latitude, loc.longitude), simulada = false)
                else mostrarUbicacion(UBICACION_SIMULADA, simulada = true)
            }
            .addOnFailureListener { mostrarUbicacion(UBICACION_SIMULADA, simulada = true) }
    }

    private fun mostrarUbicacion(posicion: LatLng, simulada: Boolean) {
        val googleMap = mapa ?: return
        googleMap.addMarker(
            MarkerOptions().position(posicion)
                .title(if (simulada) "Ubicación simulada" else "Mi ubicación")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicion, 13f))

        val origen = if (simulada) "simulada (Cusco)" else "actual"
        binding.tvUbicacion.text = "Ubicación $origen: %.4f, %.4f".format(posicion.latitude, posicion.longitude)
        destino?.let {
            val distancia = FloatArray(1)
            Location.distanceBetween(posicion.latitude, posicion.longitude, it.latitude, it.longitude, distancia)
            binding.tvUbicacion.append("\nDistancia al punto de encuentro: %.1f km".format(distancia[0] / 1000))
        }
    }

    companion object {
        private val UBICACION_SIMULADA = LatLng(-13.5170, -71.9785)
    }
}
