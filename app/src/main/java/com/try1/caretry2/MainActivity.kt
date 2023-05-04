package com.try1.caretry2

import android.content.Intent
import android.content.SyncRequest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.try1.caretry2.databinding.ActivityMainBinding
import com.try1.caretry2.ui.SafeLocations

class MainActivity : AppCompatActivity() {


    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val Latitude = intent.getDoubleExtra("Latitude",15.392583)
        val Longitude = intent.getDoubleExtra("Longitude",73.88273)
        val name = intent.getStringExtra("name")
        replacefrag(MapsFragment(name,Latitude, Longitude))

        binding.floatingActionButtonWeather.setOnClickListener {
            val intent = Intent(this, Weather::class.java)
            startActivity(intent)
        }
        binding.floatingActionButtonAddLocation.setOnClickListener{
            val intent = Intent(this,InsertLocation::class.java)
            startActivity(intent)
        }
        binding.floatingActionButtonFetchData.setOnClickListener {
            val intent=Intent(this,FetchingLoc::class.java)
            startActivity(intent)
        }


    }


    private fun replacefrag(fragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer,fragment)
        fragmentTransaction.commit()
    }
}