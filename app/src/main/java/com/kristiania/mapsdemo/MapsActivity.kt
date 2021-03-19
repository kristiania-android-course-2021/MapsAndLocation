package com.kristiania.mapsdemo

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.kristiania.mapsdemo.utils.Utils


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        addMarkers()
        addPolyline()
        addPolygon()

    }

    private fun addMarkers() {
        Utils.getGeoLocations(this)?.let { list ->
            list.map { info ->
                mMap.addMarker(
                        MarkerOptions().position(info.latLng).title(info.name)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_city))
                )
            }
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(list[list.lastIndex].latLng, 13f))
        }

        mMap.setOnMarkerClickListener(this)
    }

    private fun addPolyline(){

         mMap.addPolyline(
            PolylineOptions()
                .clickable(true)
                .color(Color.BLUE)
                .add(
                    LatLng(59.9058, 10.7552),
                    LatLng(59.9032, 10.7688),
                    LatLng(59.9349, 10.7808),
                    LatLng(	59.9213, 10.7692),
                    LatLng(59.9413, 10.7592),
                    LatLng(59.9113, 10.7492)
                ))

        mMap.setOnPolylineClickListener {

        }

    }

    private fun addPolygon(){

        mMap.addPolygon(
                PolygonOptions()
                        .fillColor(Color.argb(0x30,0x1a, 0x1a, 0x1a))
                        .clickable(true)
                        .add(
                                LatLng(59.9057, 10.7552),
                                LatLng(59.9031, 10.7688),
                                LatLng(59.9149, 10.7808),
                                LatLng(	59.9213, 10.7692),
                                LatLng(59.9313, 10.7592),
                                LatLng(59.9413, 10.7492)
                        ))

        mMap.setOnPolygonClickListener {
        }
    }

    override fun onMarkerClick(marker: Marker?) : Boolean {
        return false
    }
}

