package com.mariela.stores

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.mariela.stores.tab_fragments.tabHotelesFragment
import com.mariela.stores.tab_fragments.tabPromocionesFragment
import com.mariela.stores.tab_fragments.tabVuelosFragment

class ReservasPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> return tabVuelosFragment()
            1 -> return tabHotelesFragment()
            2 -> return tabPromocionesFragment()
        }
        throw IllegalArgumentException("Posición inválida")
    }

    override fun getCount(): Int {
        return 3 // Número total de pestañas
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Vuelos"
            1 -> "Hoteles"
            2 -> "Paquetes"

            else -> null
        }
    }

    fun getPageIcon(position: Int): Int {
        when (position) {
            0 -> return R.drawable.ic_airplane
            1 -> return R.drawable.ic_airplane
            2 -> return R.drawable.ic_airplane

        }
        throw IllegalArgumentException("Posición inválida")
    }
}