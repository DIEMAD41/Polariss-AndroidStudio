package com.mariela.stores.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputEditText
import com.mariela.stores.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.TimeZone

class HotelesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflar el diseño para este fragmento
        return inflater.inflate(R.layout.fragment_hoteles, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtener referencias a los TextInputLayout
        val tilFechaida = view.findViewById<TextInputEditText>(R.id.TIT_h_fechaida)
        val tilFecharegreso = view.findViewById<TextInputEditText>(R.id.TIT_h_fecharegreso)

        // Configurar OnClickListener para TILfechaida
        tilFechaida.setOnClickListener {

            tilFechaida.textSize = 14f
            tilFecharegreso.textSize = 14f

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.ThemeMaterialCalendar)
                .setTitleText("Seleccione las fechas de su viajes")
                .setSelection(Pair(null,null))
                .build()

            picker.show(requireActivity().supportFragmentManager,"TAG")

            picker.addOnPositiveButtonClickListener {
                tilFechaida.setText(convertTimeToDate(it.first))
                tilFecharegreso.setText(convertTimeToDate(it.second))
            }
            picker.addOnNegativeButtonClickListener{
                picker.dismiss()
            }

        }

        // Configurar OnClickListener para TILfecharegreso
        tilFecharegreso.setOnClickListener {


            tilFechaida.textSize = 14f
            tilFecharegreso.textSize = 14f

            val picker = MaterialDatePicker.Builder.dateRangePicker()
                .setTheme(R.style.ThemeMaterialCalendar)
                .setTitleText("Seleccione las fechas de su viajes")
                .setSelection(Pair(null,null))
                .build()

            picker.show(requireActivity().supportFragmentManager,"TAG")

            picker.addOnPositiveButtonClickListener {
                tilFechaida.setText(convertTimeToDate(it.first))
                tilFecharegreso.setText(convertTimeToDate(it.second))
            }
            picker.addOnNegativeButtonClickListener{
                picker.dismiss()
            }
        }

    }

    private fun convertTimeToDate(time: Long): String {
        val utc = Calendar.getInstance(TimeZone.getTimeZone("GMT"))
        utc.timeInMillis=time
        utc.add(Calendar.DAY_OF_MONTH, 1)
        val format = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return format.format((utc.time))
    }


}