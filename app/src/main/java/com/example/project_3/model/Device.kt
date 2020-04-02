package com.example.project_3.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Device(
    var id: String,
    var name: String,
    var nopol: String,
    var merk: String
) : Parcelable {
    constructor() : this("", "", "", "")
}