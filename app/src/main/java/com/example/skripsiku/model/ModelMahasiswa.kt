package com.example.skripsiku.model

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ModelMahasiswa (var id: Int, var nama: String, var nim: Int, var alamat: String, var email : String, var telepon : String ) :
    Parcelable {
    constructor() : this(0, "", 0, "", "","")

}

