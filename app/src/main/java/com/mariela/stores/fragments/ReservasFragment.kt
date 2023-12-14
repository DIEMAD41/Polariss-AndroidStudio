package com.mariela.stores.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.mariela.stores.R
import com.mariela.stores.ReservasPagerAdapter

class ReservasFragment : Fragment() {

    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var adapter: ReservasPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        val rootView = inflater.inflate(R.layout.fragment_reservas, container, false)

        // Inicializar ViewPager y TabLayout
        viewPager = rootView.findViewById(R.id.viewPager)
        tabLayout = rootView.findViewById(R.id.tabLayout)

        // Configurar el adaptador del ViewPager
        adapter = ReservasPagerAdapter(childFragmentManager)
        viewPager.adapter = adapter

        // Vincular TabLayout con ViewPager
        tabLayout.setupWithViewPager(viewPager)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configurar íconos en las pestañas después de haber vinculado el TabLayout con ViewPager
        for (i in 0 until tabLayout.tabCount) {
            tabLayout.getTabAt(i)?.setIcon(adapter.getPageIcon(i))
        }
    }
}
