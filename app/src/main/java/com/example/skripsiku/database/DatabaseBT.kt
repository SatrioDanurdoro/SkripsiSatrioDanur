package com.example.skripsiku.database

class DatabaseBT {
    companion object {
    val DATABASE_NAME = "DB_BT"
    val DATABASE_VERSION = 1

    val DATABASE_TABEL = "DB_TABEL_BT"
    val ROW_ID = "id"
    val ROW_MAC = "mac"
//    val ROW_NAME = "name"


    val QUERY_CREATE = "CREATE TABLE IF NOT EXISTS $DATABASE_TABEL ($ROW_ID INTEGER PRIMARY KEY AUTOINCREMENT, $ROW_MAC TEXT)"
    val QUERY_UPGRADE = "DROP TABLE IF EXISTS $DATABASE_TABEL"
}
}