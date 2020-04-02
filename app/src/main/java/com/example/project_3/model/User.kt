package com.example.project_3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var uid: String,
    var devices: HashMap<String, Device>
) : Parcelable {
    constructor() : this("", hashMapOf())
}