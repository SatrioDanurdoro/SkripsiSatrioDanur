package com.example.skripsiku.database

class DatabaseConstan {
    companion object {
        val DATABASE_NAME = "DB_NAME"
        val DATABASE_VERSION = 1

        val DATABASE_TABEL = "DB_TABEL"
        val ROW_ID = "_id"
        val ROW_NAMA = "nama"
        val ROW_NIM = "nim"
        val ROW_ALAMAT = "alamat"
        val ROW_EMAIL = "email"
        val ROW_TELEPON = "telepon"


        val QUERY_CREATE = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_NAMA TEXT , $ROW_NIM TEXT ,$ROW_ALAMAT TEXT , $ROW_EMAIL TEXT , $ROW_TELEPON TEXT)"
        val QUERY_UPGRADE = "DROP TABLE IF EXISTS $DATABASE_TABEL"
    }
}