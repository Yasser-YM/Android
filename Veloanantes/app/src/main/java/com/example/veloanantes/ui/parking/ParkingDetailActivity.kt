package com.example.veloanantes.ui.parking

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.veloanantes.R
import com.example.veloanantes.model.parkingSelected

class ParkingDetailActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parking_detail)
        val parkingName = findViewById<TextView>(R.id.parkingName)
        val buttonOpen = findViewById<Button>(R.id.buttonOpenMap)

        parkingSelected?.let{parking ->
            parkingName.text = parking.grpNom
            buttonOpen.setOnClickListener{
                val gmmIntentUri =
                    Uri.parse("geo:0,0?q=${parking.latitude},${parking.longitude}(${parking.grpNom}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
        }


    }
}