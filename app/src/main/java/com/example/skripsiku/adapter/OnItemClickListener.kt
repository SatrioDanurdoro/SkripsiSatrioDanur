package com.example.skripsiku.adapter

import com.example.skripsiku.model.ModelMahasiswa

interface OnItemClickListener {
    fun onClick(data : ModelMahasiswa, position : Int)
}