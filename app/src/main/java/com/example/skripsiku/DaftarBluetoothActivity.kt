//package com.example.skripsiku
//
//import android.app.Activity
//import android.content.Intent
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
////import com.example.skripsiku.bluetooth.adapter.DaftarAdapterBT
////import com.example.skripsiku.bluetooth.adapter.OnItemClickListenerBT
//import com.example.skripsiku.bluetooth.model.ModelBluetooth
//import com.example.skripsiku.database.DatabaseHelper
//import com.example.skripsiku.database.DatabaseHelperBT
//import com.example.skripsiku.model.ModelMahasiswa
//import kotlinx.android.synthetic.main.activity_daftar_mahasiswa.*
//import kotlinx.android.synthetic.main.activity_update_mahasiswa.*
//import kotlinx.android.synthetic.main.row_daftar_bt.*
//
//class DaftarBluetoothActivity  : AppCompatActivity()  {
//
//    private lateinit var dataDaftarBT: String
//    private var positionStats = 0
//
//    private var modelBluetooth = ModelBluetooth()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_daftar_mahasiswa)
//
//        dataDaftarBT = DatabaseHelperBT.getSingleData(modelBluetooth.mac)
//
//        etNameEdit.addTextChangedListener(UpdateDataMahasiswaActivity.Watcher(inNamaEdit))
//        etMACEditEdit.addTextChangedListener(UpdateDataMahasiswaActivity.Watcher(inNimEdit))
//
//        val name = etNamaEdit.text.toString()
//        val mac = etNimEdit.text.toString()
//
//        val time = etAlamatEdit.text.toString()
//
//        modelBluetooth.name = name
//        modelBluetooth.mac = mac
//        modelBluetooth.time = time
//
//
//        val stat = DatabaseHelperBT.updateData(modelBluetooth)
//
//        if (stat > 0) {
//            val bind = Bundle()
//            bind.putParcelable("DATA", modelBluetooth)
//
//            val intent = Intent()
//            intent.putExtras(bind)
//
//            setResult(Activity.RESULT_OK, intent)
//
//            list.setHasFixedSize(true)
//            list.layoutManager = LinearLayoutManager(this)
////        list.adapter = adapterDaftarBT
//
//            toolbars.title = "Daftar Mahasiswa"
//        }}
//
////        if (dataDaftarBT.size > 0) {
////            textEmpty.visibility = View.GONE
////        } else {
////            textEmpty.visibility = View.VISIBLE
////        }
//
//
////    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
////        super.onActivityResult(requestCode, resultCode, data)
////
////        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
////
////            if (data != null) {
////                val dataMahasiswa: ModelMahasiswa? = data.extras?.getParcelable("DATA")
//////                if (dataMahasiswa != null) {
//////                    dataDaftarBT[positionStats] = dataMahasiswa
//////                }
//////                adapterDaftarBT.notifyDataSetChanged()
////            }
////        }
////    }
//
////    override fun onDestroy() {
////        super.onDestroy()
////        DatabaseHelper.closeDatabase()
////    }
//
//
////    override fun onClick(data: ModelBluetooth, position: Int) {
////        TODO("Not yet implemented")
////    }
//}