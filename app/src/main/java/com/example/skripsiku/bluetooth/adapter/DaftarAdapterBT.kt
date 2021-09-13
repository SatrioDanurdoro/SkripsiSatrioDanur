//package com.example.skripsiku.bluetooth.adapter
//
//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.skripsiku.DaftarBluetoothActivity
//import com.example.skripsiku.R
//
//
//class DaftarAdapterBT(data: String, listener: DaftarBluetoothActivity) : RecyclerView.Adapter<DaftarHolderBT>() {
//
//    private val datas = data
//    private val listeners = listener
//
////    override fun getItemCount(): String = datas
////
////
////
////    override fun onBindViewHolder(holder: DaftarHolderBT, position: Int) {
////        holder?.bind(datas[position], listeners, position)
////    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarHolderBT {
//        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_daftar_bt, parent, false)
//        return DaftarHolderBT(view)
//    }
//
//    override fun onBindViewHolder(holder: DaftarHolderBT, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        TODO("Not yet implemented")
//    }
//}