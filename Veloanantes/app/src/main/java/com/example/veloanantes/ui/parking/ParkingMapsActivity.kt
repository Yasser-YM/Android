package com.example.veloanantes.ui.parking

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.veloanantes.R
import com.example.veloanantes.databinding.ActivityParkingMapsBinding

import com.example.veloanantes.model.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory


class ParkingMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityParkingMapsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityParkingMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        parkingSelected?.let { parking ->

            // Add a marker in france and move the camera
            allparking?.let {
                for(parkingSt in it) {
                    val parkingLatLng = LatLng(parkingSt.latitude, parkingSt.longitude)
                    if(parkingSt.id== parkingSelected!!.id){
                        mMap.addMarker(MarkerOptions()
                            .position(parkingLatLng)
                            .title(parking.grpNom+" "+"Dispo : "+ parking.showDetails())
                            .icon(BitmapFromVector(this,R.drawable.ic_baseline_directions_car_24)))
                    }else{
                        mMap.addMarker(MarkerOptions()
                            .position(parkingLatLng)
                            .title(parkingSt.grpNom+" "+"Dispo : " + parkingSt.showDetails()))


                    }

                }
            }
            if (currentLocation!=null){
                val location =LatLng(currentLocation!!.latitude, currentLocation!!.longitude)
                mMap.addMarker(MarkerOptions()
                    .position(location)
                    .title("My Position")
                    .icon(BitmapFromVector(this,R.drawable.ic_baseline_my_location_24)))
            }
            val current = LatLng(parking.latitude, parking.longitude)
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(current, 18f))

        }
    }
    private fun BitmapFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        // below line is use to generate a drawable.
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)

        // below line is use to set bounds to our vector drawable.
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )

        // below line is use to create a bitmap for our
        // drawable which we have added.
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )

        // below line is use to add bitmap in our canvas.
        val canvas = Canvas(bitmap)

        // below line is use to draw our
        // vector drawable in canvas.
        vectorDrawable.draw(canvas)

        // after generating our bitmap we are returning our bitmap.
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}
