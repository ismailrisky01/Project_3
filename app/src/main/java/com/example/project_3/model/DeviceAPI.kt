package com.example.project_3.model

import android.os.Parcelable
import com.google.type.LatLng
import kotlinx.android.parcel.Parcelize
import tech.jayamakmur.trackingapp.model.MyLatLng

@Parcelize
class DeviceAPI(
    var latlng: MyLatLng
) : Parcelable {
    constructor() : this(MyLatLng(0.0, 0.0))
}