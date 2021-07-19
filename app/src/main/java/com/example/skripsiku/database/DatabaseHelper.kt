package com.example.skripsiku.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.skripsiku.model.ModelMahasiswa

class DatabaseHelper (ctx: Context) : SQLiteOpenHelper(ctx, DatabaseConstan.DATABASE_NAME, null, DatabaseConstan.DATABASE_VERSION) {

    companion object {
        private lateinit var INSTANCE: DatabaseHelper
        private lateinit var database: SQLiteDatabase
        private var databaseOpen: Boolean = false

        fun closeDatabase() {
            if (database.isOpen && databaseOpen) {
                database.close()
                databaseOpen = false

                Log.i("Database" , "Database close")
            }
        }

        fun initDatabaseInstance(ctx: Context): DatabaseHelper {
            INSTANCE = DatabaseHelper(ctx)
            return INSTANCE
        }

        fun insertData(modelMahasiswa: ModelMahasiswa): Long {

            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, modelMahasiswa.nama)
            values.put(DatabaseConstan.ROW_NIM, modelMahasiswa.nim)
//            values.put(DatabaseConstan.ROW_SEMESTER, modelMahasiswa.semster)
            values.put(DatabaseConstan.ROW_ALAMAT, modelMahasiswa.alamat)
            values.put(DatabaseConstan.ROW_EMAIL, modelMahasiswa.email)
            values.put(DatabaseConstan.ROW_TELEPON, modelMahasiswa.telepon)
            return database.insert(DatabaseConstan.DATABASE_TABEL, null, values)
        }

        fun updateData(modelMahasiswa: ModelMahasiswa): Int {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val values = ContentValues()
            values.put(DatabaseConstan.ROW_NAMA, modelMahasiswa.nama)
            values.put(DatabaseConstan.ROW_NIM, modelMahasiswa.nim)
//            values.put(DatabaseConstan.ROW_SEMESTER, modelMahasiswa.semster)
            values.put(DatabaseConstan.ROW_ALAMAT, modelMahasiswa.alamat)
            values.put(DatabaseConstan.ROW_EMAIL, modelMahasiswa.email)
            values.put(DatabaseConstan.ROW_TELEPON, modelMahasiswa.telepon)
            return database.update(DatabaseConstan.DATABASE_TABEL, values, "${DatabaseConstan.ROW_ID} = ${modelMahasiswa.id}", null)
        }

        fun getAllData(): MutableList<ModelMahasiswa> {
            if (!databaseOpen) {
                database = INSTANCE.writableDatabase
                databaseOpen = true

                Log.i("Database" , "Database Open")
            }

            val data: MutableList<ModelMahasiswa> = ArrayList()
            val cursor = database.rawQuery("SELECT * FROM ${DatabaseConstan.DATABASE_TABEL}", null)
            cursor.use { cur ->
                if (cursor.moveToFirst()) {
                    do {

                        val mahasiswa = ModelMahasiswa()
                        mahasiswa.id = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_ID))
                        mahasiswa.nama = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_NAMA))
                        mahasiswa.nim = cur.getInt(cur.getColumnIndex(DatabaseConstan.ROW_NIM))
//                        mahasiswa.semster = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_SEMESTER))
                        mahasiswa.alamat = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_ALAMAT))
                        mahasiswa.email = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_EMAIL))
                        mahasiswa.telepon = cur.getString(cur.getColumnIndex(DatabaseConstan.ROW_TELEPON))
                        data.add(mahasiswa)

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
            return database.delete(DatabaseConstan.DATABASE_TABEL, "${DatabaseConstan.ROW_ID} = $id", null)
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL(DatabaseConstan.QUERY_CREATE)
        Log.i("DATABASE", "DATABASE CREATED")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL(DatabaseConstan.QUERY_UPGRADE)
        Log.i("DATABASE", "DATABASE UPDATED")
    }

}