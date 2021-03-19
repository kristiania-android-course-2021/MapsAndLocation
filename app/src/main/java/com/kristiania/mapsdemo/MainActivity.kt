package com.kristiania.mapsdemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kristiania.mapsdemo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonLocation.setOnClickListener{
            Intent(this@MainActivity, WalkActivity::class.java).also { intent->
                startActivity(intent)
            }
        }

        binding.buttonMaps.setOnClickListener{
            Intent(this@MainActivity, MapsActivity::class.java).also { intent->
                startActivity(intent)
            }
        }
    }
}