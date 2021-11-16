package com.example.skripsiku.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiku.bluetooth.model.ModelBluetooth
import kotlinx.android.synthetic.main.row_daftar_bluetooth.view.*

class DaftarHolderBT(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bind(data: ModelBluetooth, listener: OnItemClickListenerBT, position: Int) = with(itemView) {

        rowAv.text = data.name.substring(0, 1).capitalize()
        rowName.text = data.name
        rowMAC.text = data.mac
        rowTime.text = data.time
//        rowAlamat.text = data.alamat
//        rowEmail.text = data.email
//        rowTelepon.text = data.telepon

        setOnClickListener { listener.onClick(data, position) }

    }
}