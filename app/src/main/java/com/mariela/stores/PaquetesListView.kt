package com.mariela.stores

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.mariela.stores.fragments.HomeFragment
import com.mariela.stores.fragments.HotelesFragment
import com.mariela.stores.fragments.ReservasFragment
import com.mariela.stores.fragments.VuelosFragment
import com.mariela.stores.vuelosFB.Vuelo
import kotlin.math.log

class PaquetesListView : AppCompatActivity() {

    private lateinit var fragmentManager: FragmentManager
    private lateinit var currentFragment: Fragment
    private lateinit var logoImageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paquetes_list_view)



        // Inicializar el FragmentManager
        fragmentManager = supportFragmentManager

        // Obtener referencia al ImageView
        logoImageView = findViewById(R.id.logoImageView)

        // Cargar el fragmento inicial (puedes cambiarlo según tu lógica)
        currentFragment = HomeFragment()
        loadFragment(currentFragment)

        // Bottom menu
        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    currentFragment = HomeFragment()
                    updateLogoImage(R.drawable.polarisstoolbar)
                    true
                }
                R.id.action_hoteles -> {
                    currentFragment = HotelesFragment()
                    updateLogoImage(R.drawable.polarisstoolbar) // Reemplaza con el ID de la otra imagen
                    true
                }
                R.id.action_vuelos -> {
                    currentFragment = VuelosFragment()
                    updateLogoImage(R.drawable.polarisstoolbar) // Reemplaza con el ID de la otra imagen
                    true
                }
                R.id.action_reservas -> {
                    currentFragment = ReservasFragment()
                    updateLogoImage(R.drawable.polarisstoolbar) // Reemplaza con el ID de la otra imagen
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

    private fun updateLogoImage(imageResource: Int) {
        logoImageView.setImageResource(imageResource)
    }
}
