package com.example.skripsiku

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skripsiku.adapter.DaftarAdapterBT
import com.example.skripsiku.adapter.OnItemClickListenerBT
import com.example.skripsiku.bluetooth.model.ModelBluetooth
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.database.DatabaseHelperBT
import kotlinx.android.synthetic.main.activity_daftar_bluetooth.*

class DaftarBluetooth : AppCompatActivity(), OnItemClickListenerBT, DetailBluetoothDialog.OnDialogItemClick{


    private var dataDaftarBluetooth: MutableList<ModelBluetooth> = ArrayList()
    private var positionStats = 0
    lateinit private var adapterDaftarBluetooth: DaftarAdapterBT

    override fun dialogEditCallback(data: ModelBluetooth) {
        TODO("Not yet implemented")
    }

    override fun dialogDeleteCallback(data: ModelBluetooth) {
        this.dataDaftarBluetooth.remove(data)
        adapterDaftarBluetooth.notifyDataSetChanged()


        if (this.dataDaftarBluetooth.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }

    }

//
    override fun onClick(data: ModelBluetooth, position: Int) {
        DetailBluetoothDialog.newInstance(data, this).show(supportFragmentManager, "DETAIL")
//         val activityKu = DetailMahasiswaDialog.newInstance(data, this).show(supportFragmentManager, "DETAIL")
        positionStats = position
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_bluetooth)

        dataDaftarBluetooth = DatabaseHelperBT.getAllData()

        adapterDaftarBluetooth = DaftarAdapterBT(dataDaftarBluetooth, this)

        listbt.setHasFixedSize(true)
        listbt.layoutManager = LinearLayoutManager(this)
        listbt.adapter = adapterDaftarBluetooth

        toolbars.title = "Riwayat Kontak"

        if (dataDaftarBluetooth.size > 0) {
            textEmpty.visibility = View.GONE
        } else {
            textEmpty.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                val dataBluetooth: ModelBluetooth? = data.extras?.getParcelable("DATA")
                if (dataBluetooth != null) {
                    dataDaftarBluetooth[positionStats] = dataBluetooth
                }
                adapterDaftarBluetooth.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }

//    override fun onClick(data: ModelBluetooth, position: Int) {
//        TODO("Not yet implemented")
//    }
}

