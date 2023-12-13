package com.mariela.stores

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mariela.stores.fragments.HomeFragment
import com.mariela.stores.fragments.HotelesFragment
import com.mariela.stores.fragments.ReservasFragment
import com.mariela.stores.fragments.VuelosFragment

class PaquetesListView : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes_list_view)

        // Inicializar el FragmentManager
        fragmentManager = supportFragmentManager

        // Cargar el fragmento inicial (puedes cambiarlo según tu lógica)
        currentFragment = HomeFragment()
        loadFragment(currentFragment)

        // Bottom menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    currentFragment = HomeFragment()

                    true
                }
                R.id.action_hoteles -> {
                    currentFragment = HotelesFragment()
                    true
                }
                R.id.action_vuelos -> {
                    currentFragment = VuelosFragment()
                    true
                }
                R.id.action_reservas -> {
                    currentFragment = ReservasFragment()
                    true
                }
                else -> false
            }.also {
                // Actualizar el fragmento actual
                loadFragment(currentFragment)
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
