package com.example.myguider.models

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Place (var id: Int, var name : String, var image : String, var description : String, var price : String) : Parcelable {

}