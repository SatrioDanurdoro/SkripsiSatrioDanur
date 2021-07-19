package com.example.skripsiku

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skripsiku.adapter.DaftarAdapter
import com.example.skripsiku.adapter.OnItemClickListener
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.model.ModelMahasiswa

class HalamanPalingUtama : AppCompatActivity(), OnItemClickListener {
    private var dataDaftarMahasiswa : MutableList <ModelMahasiswa> = ArrayList()
    private lateinit var adapterDaftarMahasiswa: DaftarAdapter
    private var dataMahasiswa = ModelMahasiswa()

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  setContentView(R.layout.activity_halaman_paling_utama)

        dataDaftarMahasiswa = DatabaseHelper.getAllData()
        adapterDaftarMahasiswa = DaftarAdapter(dataDaftarMahasiswa, this)

        if (this.dataDaftarMahasiswa.size > 0){
            dataMahasiswa = dataDaftarMahasiswa[0]
            //   btnMulai.setOnClickListener{
            startActivity(Intent(this, HalamanUtama :: class.java))
        } else {
            // btnMulai.setOnClickListener{
            startActivity(Intent(this, TambahDataMahasiswaActivity :: class.java))
        }
    }

    override fun onClick(data: ModelMahasiswa, position: Int) {
        TODO("Not yet implemented")
    }

}