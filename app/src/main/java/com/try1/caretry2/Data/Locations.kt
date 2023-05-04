package com.try1.caretry2.Data

import android.text.Editable
import com.google.firebase.database.Exclude

data class Locations(
//    @get: Exclude
    var user:String?="null",
    var id:String?="null",
    var name:String?="null",
    var latitude: Double=0.0,
    var longitude: Double=0.0,
)
