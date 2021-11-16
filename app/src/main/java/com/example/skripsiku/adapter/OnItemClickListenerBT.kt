package com.example.skripsiku.adapter

import com.example.skripsiku.bluetooth.model.ModelBluetooth

interface OnItemClickListenerBT {
    fun onClick(data: ModelBluetooth, position: Int)
}