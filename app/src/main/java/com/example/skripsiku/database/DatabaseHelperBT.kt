package com.example.skripsiku.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.skripsiku.HalamanUtama
import com.example.skripsiku.model.ModelMahasiswa
import com.example.skripsiku.bluetooth.model.ModelBluetooth

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
//            values.put(DatabaseBT.ROW_NAME, modelBluetooth.name)

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
//            values.put(DatabaseBT.ROW_NAME, modelBluetooth.name)

////            values.put(DatabaseConstan.ROW_SEMESTER, modelMahasiswa.semster)
//            values.put(DatabaseConstan.ROW_ALAMAT, modelMahasiswa.alamat)
//            values.put(DatabaseConstan.ROW_EMAIL, modelMahasiswa.email)
//            values.put(DatabaseConstan.ROW_TELEPON, modelMahasiswa.telepon)
            return database.update(DatabaseBT.DATABASE_TABEL, values, "${DatabaseBT.ROW_ID} = ${modelBluetooth.id}", null)
        }

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
//                        bluetooth.name = cur.getString(cur.getColumnIndex(DatabaseBT.ROW_NAME))
                        data.add(bluetooth)

//                        val mahasiswa = ModelMahasiswa()
//                        mahasiswa.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
//                        mahasiswa.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
//                        mahasiswa.nim = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_NIM))
////                        mahasiswa.semster = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_SEMESTER))
//                        mahasiswa.alamat = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_ALAMAT))
//                        mahasiswa.email = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_EMAIL))
//                        mahasiswa.telepon = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_TELEPON))
//                        data.add(mahasiswa)

                    } while (cursor.moveToNext())
                }
            }
            return data
        }

        fun deleteData(id: Int): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }
            return database.delete(DatabaseBT.DATABASE_TABEL, "${DatabaseBT.ROW_ID} = $id", null)
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