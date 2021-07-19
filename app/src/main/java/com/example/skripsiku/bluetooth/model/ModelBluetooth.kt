package com.example.skripsiku.bluetooth.model


import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
class ModelBluetooth (var id: Int, var mac: String, var name: String) :
    Parcelable {
    constructor() : this(0, "", "")

}