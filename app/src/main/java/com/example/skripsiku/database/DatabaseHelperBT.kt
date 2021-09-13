package com.example.skripsiku.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.ContactsContract
import android.util.Log
import androidx.room.Query
import com.example.skripsiku.HalamanUtama
import com.example.skripsiku.model.ModelMahasiswa
import com.example.skripsiku.bluetooth.model.ModelBluetooth
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.select
import org.jetbrains.anko.db.update
import java.security.KeyStore
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DatabaseHelperBT (ctx: Context) : SQLiteOpenHelper(ctx, DatabaseBT.DATABASE_NAME, null, DatabaseBT.DATABASE_VERSION) {

    companion object {
        private lateinit var INSTANCE: DatabaseHelperBT
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database" , "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): DatabaseHelperBT {
            INSTANCE = DatabaseHelperBT(ctx)
            return INSTANCE
        }

        fun insertData(modelBluetooth: ModelBluetooth): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseBT.ROW_MAC, modelBluetooth.mac)
            values.put(DatabaseBT.ROW_NAME, modelBluetooth.name)
            values.put(DatabaseBT.ROW_TIME, modelBluetooth.time)

////            values.put(DatabaseConstan.ROW_SEMESTER, modelMahasiswa.semster)
//            values.put(DatabaseConstan.ROW_ALAMAT, modelMahasiswa.alamat)
//            values.put(DatabaseConstan.ROW_EMAIL, modelMahasiswa.email)
//            values.put(DatabaseConstan.ROW_TELEPON, modelMahasiswa.telepon)
            return database.insert(DatabaseBT.DATABASE_TABEL, null, values)
        }

        fun updateData(modelBluetooth: ModelBluetooth): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseBT.ROW_MAC, modelBluetooth.mac)
            values.put(DatabaseBT.ROW_NAME, modelBluetooth.name)
            values.put(DatabaseBT.ROW_TIME, modelBluetooth.time)

//            return database.update(DatabaseBT.DATABASE_TABEL, values, "${DatabaseBT.ROW_MAC}", null)
            return database.update(DatabaseBT.DATABASE_TABEL, values, "${DatabaseBT.ROW_MAC} = '${modelBluetooth.mac}'", null)
        }

//        fun updateData(modelBluetooth: ModelBluetooth): Boolean{
//            if (!databaseOpen) {
//                database = INSTANCE.writableDatabase
//                databaseOpen = true
//
//                Log.i("Database" , "Database Open")
//            }
//
//            val values = ContentValues()
//            values.put(DatabaseBT.ROW_MAC, modelBluetooth.mac)
//            values.put(DatabaseBT.ROW_NAME, modelBluetooth.name)
//            values.put(DatabaseBT.ROW_TIME, modelBluetooth.time)
//            database.update(DatabaseBT.DATABASE_TABEL, values, "${DatabaseBT.ROW_MAC} = ${modelBluetooth.mac}?s", null)
//
//            return true
//        }

        fun getAllData(): MutableList<ModelBluetooth> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<ModelBluetooth> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DatabaseBT.DATABASE_TABEL}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val bluetooth = ModelBluetooth()
                        bluetooth.id = cur.getInt(cur.getColumnIndex(DatabaseBT.ROW_ID))
                        bluetooth.mac = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_MAC))
                        bluetooth.name = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_NAME))
                        bluetooth.time = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_TIME))
                        data.add(bluetooth)


                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun getSingleData(modelBluetooth: ModelBluetooth): String {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            val bluetooth = ModelBluetooth()
            val data: MutableList<ModelBluetooth> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DatabaseBT.DATABASE_TABEL} WHERE ${DatabaseBT.ROW_MAC} = '${modelBluetooth.mac}'", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {


                        bluetooth.id = cur.getInt(cur.getColumnIndex(DatabaseBT.ROW_ID))
                        bluetooth.mac = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_MAC))
                        bluetooth.name = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_NAME))
                        bluetooth.time = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_TIME))
                        data.add(bluetooth)


                    } while (cursor.moveToNext())
                }
            }
            return bluetooth.mac
        }

        fun getDataTime(modelBluetooth: ModelBluetooth) : LocalDateTime? {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val bt = ModelBluetooth()
            val data : MutableList<ModelBluetooth> = ArrayList()

            val cursor = database.rawQuery("SELECT * FROM ${DatabaseBT.DATABASE_TABEL} WHERE ${DatabaseBT.ROW_MAC} = '${modelBluetooth.mac}'", null)
//
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {
                        bt.id = cur.getInt(cur.getColumnIndex(DatabaseBT.ROW_ID))
                        bt.name = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_NAME))
                        bt.mac = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_MAC))
//                        bluetooth.name = cur.getString(cur.getColumnIndex(DatabaseBluetooth.ROW_NAME))
                        bt.time = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_TIME))
//                        bt.timeDown = cur.getString(cur.getColumnIndex(DDatabaseBT.ROW_TIME_DOWN))
                        data.add(bt)


                    } while (cursor.moveToNext())
                }
            }

            if (bt.time== null){
                return null
                Log.i("getTime" , "0")
            }else {

                val format = DateTimeFormatter.ofPattern("yyyy/MM/dd    HH:mm:ss:SSSS")
                var mUP = LocalDateTime.parse(bt.time, format)
                Log.i("getTimeNOT NULL", bt.time)
                return mUP
            }

        }

        fun deleteData(mac: String): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            Log.i("fun Delete Data" , mac)
//            return database.delete(DatabaseBT.DATABASE_TABEL, "${DatabaseBT.ROW_MAC}", null)
            return database.delete(DatabaseBT.DATABASE_TABEL, "${DatabaseBT.ROW_MAC} = '$mac'", null)

        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DatabaseBT.QUERY_CREATE)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DatabaseBT.QUERY_UPGRADE)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}