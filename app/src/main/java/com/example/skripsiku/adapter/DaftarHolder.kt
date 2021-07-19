package com.example.skripsiku.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.skripsiku.model.ModelMahasiswa
import kotlinx.android.synthetic.main.row_daftar_mahasiswa.view.*

class DaftarHolder (itemView: View?) : RecyclerView.ViewHolder(itemView!!) {

    fun bind(data: ModelMahasiswa, listener: OnItemClickListener, position: Int) = with(itemView) {

        rowAv.text = data.nama.substring(0, 1).capitalize()
        rowNama.text = data.nama
        rowNim.text = data.nim.toString()
//        rowAlamat.text = data.alamat
//        rowEmail.text = data.email
//        rowTelepon.text = data.telepon

        setOnClickListener { listener.onClick(data, position) }

    }
}