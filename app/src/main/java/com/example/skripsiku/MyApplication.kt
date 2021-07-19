package com.example.skripsiku

import android.app.Application
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.database.DatabaseHelperBT

class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        DatabaseHelper.initDatabaseInstance(this)
        DatabaseHelperBT.initDatabaseInstance(this)
    }

}
