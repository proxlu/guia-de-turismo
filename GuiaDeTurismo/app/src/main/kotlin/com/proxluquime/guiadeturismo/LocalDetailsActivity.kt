package com.proxluquime.guiadeturismo

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LocalDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_local_details)

        val locationNameTextView = findViewById<TextView>(R.id.locationNameTextView)
        val locationDescriptionTextView = findViewById<TextView>(R.id.locationDescriptionTextView)

        val locationName = intent.getStringExtra("locationName")
        val locationDescription = intent.getStringExtra("locationDescription")

        locationNameTextView.text = locationName
        locationDescriptionTextView.text = locationDescription
    }
}
