package com.kristiania.mapsdemo.utils

import android.content.Context
import com.cocoahero.android.geojson.FeatureCollection
import com.cocoahero.android.geojson.GeoJSON
import com.google.android.gms.maps.model.LatLng
import com.kristiania.mapsdemo.R
import org.json.JSONException
import java.io.*


object Utils {

    fun getGeoLocations(context: Context) : ArrayList<LocationInfo>? {

        var collection = try {
            val geoJSONObject = GeoJSON.parse(readJSONFile(context))
             geoJSONObject?.let {
                FeatureCollection(geoJSONObject.toJSON())
            }
        }
        catch (e: JSONException) {
            e.printStackTrace()
            null
        }

        var list = ArrayList<LocationInfo>().apply {

            collection?.features?.map { feature->
                add(LocationInfo(feature.properties.getString("name"),
                    LatLng( feature.geometry.toJSON().getJSONArray("coordinates").getDouble(1),
                    feature.geometry.toJSON().getJSONArray("coordinates").getDouble(0)),
                    feature.properties.getString("kinds") ))
            }
        }

        return list
    }

    private fun readJSONFile(context: Context) : String? {
        val inputStream = context.resources.openRawResource(R.raw.locations)

        val writer: Writer = StringWriter()
        val buffer = CharArray(1024)
        try {
            val reader = BufferedReader(InputStreamReader(inputStream, "UTF-8"))
            var n: Int
            while (reader.read(buffer).also { n = it } != -1) {
                writer.write(buffer, 0, n)
            }
        } finally {
            inputStream.close()
        }

        return writer.toString()
    }

}