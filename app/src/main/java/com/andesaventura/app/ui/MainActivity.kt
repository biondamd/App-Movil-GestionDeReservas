package com.andesaventura.app.ui

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.andesaventura.app.R
import com.andesaventura.app.databinding.ActivityMainBinding
import com.andesaventura.app.ui.fragments.ContactosFragment
import com.andesaventura.app.ui.fragments.InicioFragment
import com.andesaventura.app.ui.fragments.LoginFragment
import com.andesaventura.app.ui.fragments.NosotrosFragment

/** Home con Navigation Drawer: Inicio, Nosotros, Contactos y Login (Fragments). */
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        configurarToolbar(binding.toolbar, getString(R.string.menu_inicio), conRetroceso = false)
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.toolbar, R.string.abrir_menu, R.string.cerrar_menu
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener { item ->
            mostrar(item.itemId)
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        if (savedInstanceState == null) {
            binding.navView.setCheckedItem(R.id.nav_inicio)
            mostrar(R.id.nav_inicio)
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    finish()
                }
            }
        })
    }

    private fun mostrar(itemId: Int) {
        val (fragment: Fragment, titulo) = when (itemId) {
            R.id.nav_nosotros -> NosotrosFragment() to R.string.menu_nosotros
            R.id.nav_contactos -> ContactosFragment() to R.string.menu_contactos
            R.id.nav_login -> LoginFragment() to R.string.menu_login
            else -> InicioFragment() to R.string.menu_inicio
        }
        supportActionBar?.title = getString(titulo)
        supportFragmentManager.beginTransaction()
            .replace(R.id.contenedor, fragment)
            .commit()
    }
}
