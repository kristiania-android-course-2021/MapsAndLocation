package com.kristiania.mapsdemo

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PolylineOptions


class WalkActivity : AppCompatActivity(), OnMapReadyCallback {

    private val REQUEST_CODE = 100

    private lateinit var map: GoogleMap
    private var fusedLocationClient: FusedLocationProviderClient? = null

    private val locationRequest = LocationRequest.create().apply {
        interval = 3000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        smallestDisplacement = 3f
        fastestInterval  = 2000
    }

    var path = ArrayList<LatLng>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_walk)
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }

    private var locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {

            locationResult?.let {
                for (location in locationResult.locations) {
                    path.add(LatLng(location.latitude, location.longitude))
                }
            }

            if( path.size > 2 ){
                map.addPolyline(
                    PolylineOptions()
                        .clickable(true)
                        .color(Color.RED)
                        .add(
                            path[path.lastIndex-1],
                            path[path.lastIndex],
                        ))
                map.moveCamera(CameraUpdateFactory.newLatLngZoom( path[path.lastIndex], 21f))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    fun startLocationUpdates(){

        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission (
                this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                REQUEST_CODE
            )
            return
        }

        fusedLocationClient?.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())

    }

    fun stopLocationUpdates(){
        fusedLocationClient?.removeLocationUpdates(locationCallback)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
    }
}