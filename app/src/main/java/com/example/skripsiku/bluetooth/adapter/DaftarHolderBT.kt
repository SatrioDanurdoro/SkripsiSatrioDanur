//package com.example.skripsiku.bluetooth.adapter
//
//import android.view.View
//import androidx.recyclerview.widget.RecyclerView
//import com.example.skripsiku.adapter.OnItemClickListener
//import com.example.skripsiku.bluetooth.model.ModelBluetooth
//import kotlinx.android.synthetic.main.row_daftar_bt.view.*
//
//class DaftarHolderBT (itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
//
//    fun bind(data: ModelBluetooth, listener: OnItemClickListenerBT, position: Int) = with(itemView) {
//
//        rowAb.text = data.mac.substring(0, 1).capitalize()
//        rowMac.text = data.mac
//        rowTime.text = data.time.toString()
////        rowAlamat.text = data.alamat
////        rowEmail.text = data.email
////        rowTelepon.text = data.telepon
//
//        setOnClickListener { listener.onClick(data, position) }
//
//    }
//}