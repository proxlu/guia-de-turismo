package com.proxluquime.guiadeturismo

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class LocationAdapter(private val context: Context, private val locations: MutableList<android.location.Location>) : BaseAdapter() {
    override fun getCount(): Int {
        return locations.size
    }

    override fun getItem(position: Int): Any {
        return locations[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.location_item, parent, false)

        val location = getItem(position) as Location

        val locationNameTextView = view.findViewById<TextView>(R.id.locationNameTextView)
        val distanceTextView = view.findViewById<TextView>(R.id.distanceTextView)

        locationNameTextView.text = location.name
        distanceTextView.text = "Distance: ${calculateDistance(location.latitude, location.longitude)} meters"

        view.setOnClickListener {
            // Implementar a ação para exibir detalhes do local quando o nome do local for clicado
        }

        distanceTextView.setOnClickListener {
            // Implementar a ação para exibir o mapa e a rota quando a distância for clicada
        }

        return view
    }

    private fun calculateDistance(latitude: Double, longitude: Double): Double {
        // Implementar o cálculo da distância entre a localização atual do usuário e as coordenadas do local
        return 0.0
    }
}
