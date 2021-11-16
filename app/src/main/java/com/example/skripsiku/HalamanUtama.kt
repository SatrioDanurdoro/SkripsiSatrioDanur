package com.example.skripsiku

import android.Manifest
import android.app.Activity
import android.app.Service
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.bluetooth.le.ScanSettings
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
//import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
//import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.skripsiku.adapter.DaftarAdapter
import com.example.skripsiku.adapter.OnItemClickListener
import com.example.skripsiku.bluetooth.BleOperationsActivity
//import com.example.skripsiku.bluetooth.MainActivity
import com.example.skripsiku.bluetooth.ScanResultAdapter
import com.example.skripsiku.bluetooth.ble.ConnectionEventListener
import com.example.skripsiku.bluetooth.ble.ConnectionManager
import com.example.skripsiku.bluetooth.model.ModelBluetooth
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.database.DatabaseHelperBT
import com.example.skripsiku.model.ModelMahasiswa
import kotlinx.android.synthetic.main.activity_halaman_utama.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tambah_mahasiswa.*
import kotlinx.android.synthetic.main.row_scan_result.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.appcompatV7.BuildConfig
import timber.log.Timber
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*
import kotlin.collections.ArrayList
import kotlin.properties.Delegates

// TOKEN GITHUB
// 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


private const val ENABLE_BLUETOOTH_REQUEST_CODE = 1
private const val LOCATION_PERMISSION_REQUEST_CODE = 2

class HalamanUtama : AppCompatActivity() , OnItemClickListener {
    var dataMahasiswa = ModelMahasiswa()
    var dataDaftarMahasiswa: MutableList<ModelMahasiswa> = ArrayList()

    var bt = ModelBluetooth()
    var singledata : String?=""
    var dataDaftarKoneksi : MutableList<ModelBluetooth> = ArrayList()


    private var positionStats = 1
    lateinit private var adapterDaftarMahasiswa: DaftarAdapter



    private val bluetoothAdapter: BluetoothAdapter by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    private val bleScanner by lazy {
        bluetoothAdapter.bluetoothLeScanner
    }

    private val scanSettings = ScanSettings.Builder()
        .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
        .build()

    private var isScanning = false
        set(value) {
            field = value
//            runOnUiThread { btn_bluetooth.text = if (value) "Stop Scan" else "Start Scan" }
        }

    private val scanResults = mutableListOf<ScanResult>()
    private val scanResultAdapter: ScanResultAdapter by lazy {
        ScanResultAdapter(scanResults) { result ->
            if (isScanning) {
                stopBleScan()
            }
            with(result.device) {
                Timber.w("Connecting to $address")
                ConnectionManager.connect(this, this@HalamanUtama)
            }
        }
    }

    private val isLocationPermissionGranted
        get() = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)

    /*******************************************
     * Activity function overrides
     *******************************************/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_utama)

        dataDaftarMahasiswa = DatabaseHelper.getAllData()

        adapterDaftarMahasiswa = DaftarAdapter(dataDaftarMahasiswa, this)


        dataMahasiswa = dataDaftarMahasiswa[0]


        dialogNama.text = dataMahasiswa.nama.toUpperCase()
        dialogNim.text = dataMahasiswa.nim.toString()
        dialogAlamat.text = dataMahasiswa.alamat
        dialogEmail.text = dataMahasiswa.email
        dialogTelepon.text = dataMahasiswa.telepon
        dialogMAC.text = singledata


        btnEdit.setOnClickListener {
            dialogEditCallback(dataMahasiswa)
        }

        btnLihatBT.setOnClickListener {
            startActivity(Intent(this, DaftarBluetooth::class.java))
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
//        btn_bluetooth.setOnClickListener { if (isScanning) stopBleScan() else startBleScan() }
//        setupRecyclerView()



        if (isScanning){
            startBleScan()
        }else{
            startBleScan()
        }
        setupRecyclerView()

        deletedata()
//        Log.i("Oncreate" , "Delete Data")

        Service.START_STICKY
//
    }




    interface OnDialogItemClick {
        fun dialogEditCallback(data: ModelMahasiswa)
        fun dialogDeleteCallback(data: ModelMahasiswa)
    }

    fun dialogEditCallback(data: ModelMahasiswa) {

        val bind = Bundle()
        bind.putParcelable("DATA", data)

        val edit = Intent(this, UpdateDataMahasiswaActivity::class.java)
        edit.putExtras(bind)
        startActivityForResult(edit, 1)
    }

//    fun btnBluetooth(view : View){
//        val intent = Intent(this, MainActivity :: class.java)
//        startActivity(intent)
//    }


    override fun onDestroy() {
        super.onDestroy()
//        DatabaseHelper.closeDatabase()
    }

//    override fun onClick(data: ModelBluetooth, position: Int) {
//        TODO("Not yet implemented")
//    }

override fun onResume() {
    super.onResume()
    ConnectionManager.registerListener(connectionEventListener)
    if (!bluetoothAdapter.isEnabled) {
        promptEnableBluetooth()
    }
}

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    when (requestCode) {
        ENABLE_BLUETOOTH_REQUEST_CODE -> {
            if (resultCode != Activity.RESULT_OK) {
                promptEnableBluetooth()
            }
        }
    }
}

override fun onRequestPermissionsResult(
    requestCode: Int,
    permissions: Array<out String>,
    grantResults: IntArray
) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    when (requestCode) {
        LOCATION_PERMISSION_REQUEST_CODE -> {
            if (grantResults.firstOrNull() == PackageManager.PERMISSION_DENIED) {
                requestLocationPermission()
            } else {
                startBleScan()
            }
        }
    }
}

/*******************************************
 * Private functions
 *******************************************/

private fun promptEnableBluetooth() {
    if (!bluetoothAdapter.isEnabled) {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        startActivityForResult(enableBtIntent,
            ENABLE_BLUETOOTH_REQUEST_CODE
        )
    }
}

private fun startBleScan() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !isLocationPermissionGranted) {
        requestLocationPermission()
    } else {
        scanResults.clear()
        scanResultAdapter.notifyDataSetChanged()
        bleScanner.startScan(null, scanSettings, scanCallback)
        isScanning = true

        val discoveryIntent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        discoveryIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, Service.START_STICKY)
        startActivity(discoveryIntent)
    }
}

private fun stopBleScan() {
    bleScanner.stopScan(scanCallback)
    isScanning = false
}

private fun requestLocationPermission() {
    if (isLocationPermissionGranted) {
        return
    }
    runOnUiThread {
        alert {
            title = "Location permission required"
            message = "Starting from Android M (6.0), the system requires apps to be granted " +
                    "location access in order to scan for BLE devices."
            isCancelable = false
            positiveButton(android.R.string.ok) {
                requestPermission(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }.show()
    }
}

private fun setupRecyclerView() {
    scan_results_recycler_view.apply {
        adapter = scanResultAdapter
        layoutManager = LinearLayoutManager(
            this@HalamanUtama,
            RecyclerView.VERTICAL,
            false
        )
        isNestedScrollingEnabled = false
    }

    val animator = scan_results_recycler_view.itemAnimator
    if (animator is SimpleItemAnimator) {
        animator.supportsChangeAnimations = false
    }
}

    private fun deletedata() {
        var systemTime = System.currentTimeMillis()
        val format = DateTimeFormatter.ofPattern("yyyy/MM/dd    HH:mm:ss:SSSS")
        var timeNow = LocalDateTime.parse(formatTimestamp(systemTime), format)
        dataDaftarKoneksi = DatabaseHelperBT.getAllData()
        var timeUp : LocalDateTime? = null
//        var selisih : Long = 0
        var selisih by Delegates.notNull<Long>()
        Log.i("Delete Data" , "delete data")

        var counter = 0

        for (i in dataDaftarKoneksi){
            timeUp = DatabaseHelperBT.getDataTime(dataDaftarKoneksi[counter])
            if (timeUp != null){
                selisih = ChronoUnit.DAYS.between(timeUp,timeNow)
                Log.i("Time UP" , selisih.toString())

                if(selisih > 1){
                    DatabaseHelperBT.deleteData(dataDaftarKoneksi[counter].mac)
                    Log.i("Data Delete" , dataDaftarKoneksi[counter].mac)
                }
            }



            counter = counter+1
        }

    }

//    private fun formatTimestamp(systemTime: Long): Any {
//
//    }

    /*******************************************
 * Callback bodies
 *******************************************/

private val scanCallback = object : ScanCallback() {
    override fun onScanResult(callbackType: Int, result: ScanResult) {

//        Log.i("OnScanResult" , "Delete Data")
        deletedata()
        bt.mac = result.device.address

        val systemTime = System.currentTimeMillis()
        bt.time = formatTimestamp(systemTime)

        if (result.device.name != null) {
            bt.name = result.device.name
        }else{
            bt.name = "Nama Tidak Diketahui"
        }



//        DatabaseHelperBT.deleteData(bt)



        val indexQuery = scanResults.indexOfFirst { it.device.address == result.device.address }
        if (indexQuery != -1) { // A scan result already exists with the same address
            scanResults[indexQuery] = result
            scanResultAdapter.notifyItemChanged(indexQuery)
//
//            DatabaseHelperBT.deleteData(bt)

//            bt.mac = result.device.address
//
//            val systemTime = System.currentTimeMillis()
//            bt.time = formatTimestamp(systemTime)
//
//            if (result.device.name != null) {
//                bt.name = result.device.name
//            }else{
//                bt.name = "Nama Tidak Diketahui"
//            }
//
            DatabaseHelperBT.updateData(bt)

//            val stat = DatabaseHelperBT.updateData(bt)
//            if (stat>0) {
//                val bind = Bundle()
//                bind.putParcelable("DATA", bt)
//
//                val intent = Intent()
//                intent.putExtras(bind)
//
//                setResult(Activity.RESULT_OK, intent)
//            }


        } else {
            with(result.device) {
                Timber.i("Found BLE device! Name: ${name ?: "Nama TIdak Diketahui"}, address: $address")
            }
            scanResults.add(result)
            scanResultAdapter.notifyItemInserted(scanResults.size - 1)
            Log.i("getSingleData", DatabaseHelperBT.getSingleData(bt)+"            "+result.device.address)
//Insert Data Scan ke database
            if (DatabaseHelperBT.getSingleData(bt) != result.device.address){


                singledata = "beda"

//                    var bt = ModelBluetooth()
//                    bt.mac = result.device.address
//
//                    val systemTime = System.currentTimeMillis()
//                    bt.time = formatTimestamp(systemTime)
//
//                        if (result.device.name != null) {
//                        bt.name = result.device.name
//                    }else{
//                        bt.name = "Nama Tidak Diketahui"
//                    }

//                DatabaseHelperBT.updateData(bt)
//                DatabaseHelperBT.deleteData(bt)
                DatabaseHelperBT.insertData(bt)


//                val stat = DatabaseHelperBT.updateData(bt)
//                if (stat>0){
//                    val bind = Bundle()
//                    bind.putParcelable("DATA", bt)
//
//                    val intent = Intent()
//                    intent.putExtras(bind)
//
//                    setResult(Activity.RESULT_OK, intent)

//                    Toast.makeText(this, "Berhasil Mengupdate Data", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Gagal Mengudate Data", Toast.LENGTH_SHORT).show()
//                }


            }else{

                singledata = "sama"

//                val bt = ModelBluetooth()
//                    bt.mac = result.device.address
//
//                val systemTime = System.currentTimeMillis()
//                bt.time = formatTimestamp(systemTime)
//
//                        if (result.device.name != null) {
//                        bt.name = result.device.name
//                    }else{
//                        bt.name = "Nama Tidak Diketahui"
//                    }
////                DatabaseHelperBT.deleteData(result.device.address)
//                DatabaseHelperBT.deleteData(bt)
//                DatabaseHelperBT.insertData(bt)
                DatabaseHelperBT.updateData(bt)
            }
        }
    }



    override fun onScanFailed(errorCode: Int) {
        Timber.e("onScanFailed: code $errorCode")
    }
}

    private fun formatTimestamp(time: Long): String {
        val format = SimpleDateFormat("yyyy/MM/dd    HH:mm:ss:SSSS", Locale.ENGLISH)
        return format.format(Date(time))
    }

private val connectionEventListener by lazy {
    ConnectionEventListener().apply {
        onConnectionSetupComplete = { gatt ->
            Intent(this@HalamanUtama, BleOperationsActivity::class.java).also {
                it.putExtra(BluetoothDevice.EXTRA_DEVICE, gatt.device)
                startActivity(it)
            }
            ConnectionManager.unregisterListener(this)
        }
        onDisconnect = {
            runOnUiThread {
                alert {
                    title = "Disconnected"
                    message = "Disconnected or unable to connect to device."
                    positiveButton("OK") {}
                }.show()
            }
        }
    }
}

/*******************************************
 * Extension functions
 *******************************************/

private fun Context.hasPermission(permissionType: String): Boolean {
    return ContextCompat.checkSelfPermission(this, permissionType) ==
            PackageManager.PERMISSION_GRANTED
}

private fun Activity.requestPermission(permission: String, requestCode: Int) {
    ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
}

    override fun onClick(data: ModelMahasiswa, position: Int) {
        TODO("Not yet implemented")
    }

}
