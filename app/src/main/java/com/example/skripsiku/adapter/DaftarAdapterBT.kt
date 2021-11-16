package com.example.skripsiku.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiku.R
import com.example.skripsiku.bluetooth.model.ModelBluetooth

class DaftarAdapterBT (data: MutableList<ModelBluetooth>, listener: OnItemClickListenerBT) : RecyclerView.Adapter<DaftarHolderBT>() {

    private val datas = data
    private val listeners = listener

    override fun getItemCount(): Int = datas.size



    override fun onBindViewHolder(holder: DaftarHolderBT, position: Int) {
        holder?.bind(datas[position], listeners, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaftarHolderBT {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.row_daftar_bluetooth, parent, false)
        return DaftarHolderBT(view)
    }
}