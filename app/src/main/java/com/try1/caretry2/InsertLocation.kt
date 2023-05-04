package com.try1.caretry2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.try1.caretry2.Data.Locations

class InsertLocation : AppCompatActivity() {
    private lateinit var etLocName: EditText
    private lateinit var etLatitude: EditText
    private lateinit var etLongitude: EditText
    private lateinit var btnSaveData: Button
    private lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert_location)
        etLocName=findViewById(R.id.etLocName)
        etLatitude=findViewById(R.id.etLatitude)
        etLongitude=findViewById(R.id.etLongitude)
        btnSaveData=findViewById(R.id.btnSave)
        dbRef = FirebaseDatabase.getInstance().getReference("Location")
        btnSaveData.setOnClickListener {
            saveLocationData()
        }
    }
    private fun saveLocationData()
    {
        val LocName=etLocName.text.toString()
        val latitude = etLatitude.text.toString().toDouble()
        val longitude = etLongitude.text.toString().toDouble()

        val locId = dbRef.push().key!!

        val user = FirebaseAuth.getInstance().currentUser


        if (user != null) {

            val uid = user.uid
            val location = Locations(uid,locId,LocName, latitude,longitude)
            dbRef.child(uid).child(LocName).setValue(location)
                .addOnSuccessListener {
                    Toast.makeText(this, "Succes Budddy", Toast.LENGTH_SHORT).show()
                    etLongitude.text.clear()
                    etLocName.text.clear()
                    etLatitude.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "shitt", Toast.LENGTH_SHORT).show()
                }
        }
        else
        {
            Toast.makeText(this, "no user?!!?", Toast.LENGTH_SHORT).show()
        }

    }
}