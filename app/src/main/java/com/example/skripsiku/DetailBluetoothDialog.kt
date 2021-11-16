package com.example.skripsiku

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.skripsiku.bluetooth.model.ModelBluetooth
import com.example.skripsiku.database.DatabaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_detail_bluetooth.*

class DetailBluetoothDialog : BottomSheetDialogFragment(){

    private var dataBluetooth = ModelBluetooth()

    companion object {
        lateinit  private  var listeners: OnDialogItemClick

        fun newInstance(data: ModelBluetooth, listener: OnDialogItemClick): DetailBluetoothDialog {

            listeners = listener
            val detail = DetailBluetoothDialog()

            val bind = Bundle()
            bind.putParcelable("DATA", data)

            detail.arguments = bind
            return detail

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments

        if (args != null)
            dataBluetooth = args.getParcelable("DATA")!!
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_detail_bluetooth, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogName.text = dataBluetooth.name.toUpperCase()
        dialogMAC.text = dataBluetooth.mac
        dialogTime.text = dataBluetooth.time


        toolbarDialog.inflateMenu(R.menu.dialog_menu)
        toolbarDialog.setOnMenuItemClickListener {

//            when (it.itemId) {
//
//                R.id.dialogEdit -> {
            listeners.dialogEditCallback(dataBluetooth)
//                    dialog.dismiss()
//                }
//                R.id.dialogHapus -> {
//                    val build = context?.let { it1 -> AlertDialog.Builder(it1) }
//                    build?.setTitle("Hapus Data")
//                    build?.setMessage("Apakah Kamu Ingin Menghapus Data ${dataMahasiswa.nama.toUpperCase()}")
//                    build?.setPositiveButton("HAPUS", { _, _ ->
//
//                        val stas = DatabaseHelper.deleteData(dataMahasiswa.id)
//
//                        if (stas != 0) {
//                            dialog.dismiss()
//                            listeners.dialogDeleteCallback(dataMahasiswa)
//
//                            Toast.makeText(activity, "Berhasil Menghapus Data", Toast.LENGTH_SHORT).show()
//                        } else {
//                            Toast.makeText(activity, "Gagal Menghapus Data", Toast.LENGTH_SHORT).show()
//                        }
//
//                    })
//                    build?.setNegativeButton("BATAL", null)
//                    build?.create()?.show()
//                }
//            }
            true
        }
    }

    interface OnDialogItemClick {
        fun dialogEditCallback(data: ModelBluetooth)
        fun dialogDeleteCallback(data: ModelBluetooth)
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }
}