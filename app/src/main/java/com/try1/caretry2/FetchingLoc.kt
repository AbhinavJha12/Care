package com.try1.caretry2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.try1.caretry2.Data.Locations
import com.try1.caretry2.adapter.LocAdapter

class FetchingLoc : AppCompatActivity() {
    private lateinit var locRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var locList:ArrayList<Locations>
    private lateinit var dbRef:DatabaseReference
    private lateinit var uid:String

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching_loc)
        locRecyclerView=findViewById(R.id.rvEmp)
        locRecyclerView.layoutManager= LinearLayoutManager(this)
        locRecyclerView.setHasFixedSize(true)

        tvLoadingData=findViewById(R.id.tvLoadingData)
        locList = arrayListOf<Locations>()

        getLocData()
    }

    private fun getLocData()
    {
        locRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
            uid = user.uid
        }
        dbRef = FirebaseDatabase.getInstance().getReference("Location").child(uid)
        dbRef.addValueEventListener(object :ValueEventListener{
            /**
             * This method will be called with a snapshot of the data at this location. It will also be called
             * each time that data changes.
             *
             * @param snapshot The current data at the location
             */
            override fun onDataChange(snapshot: DataSnapshot) {
                locList.clear()
                if(snapshot.exists())
                {
                    for(locSnap in snapshot.children ){
                        val locData = locSnap.getValue(Locations::class.java)
                        locList.add(locData!!)
                    }
                    val mAdapter = LocAdapter(locList)
                    locRecyclerView.adapter=mAdapter

                    mAdapter.setOnItemClickListener(object :LocAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingLoc,MainActivity::class.java)
                            intent.putExtra("name",locList[position].name)
                            intent.putExtra("Latitude",locList[position].latitude)
                            intent.putExtra("Longitude",locList[position].longitude)
                            startActivity(intent)
                        }

                    })

                    locRecyclerView.visibility=View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            /**
             * This method will be triggered in the event that this listener either failed at the server, or
             * is removed as a result of the security and Firebase Database rules. For more information on
             * securing your data, see: [ Security
 * Quickstart](https://firebase.google.com/docs/database/security/quickstart)
             *
             * @param error A description of the error that occurred
             */
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }
}