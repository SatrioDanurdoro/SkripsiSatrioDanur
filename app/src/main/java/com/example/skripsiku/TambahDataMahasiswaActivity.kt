package com.example.skripsiku

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.model.ModelMahasiswa
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_tambah_mahasiswa.*

class TambahDataMahasiswaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_mahasiswa)

        toolbar.title = "SQLite Kotlin2"

        etNama.addTextChangedListener(Watcher(inNama))
        etNim.addTextChangedListener(Watcher(inNim))

//        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getSemester())
//        spinnerSemester.adapter = adapter

        btnInsert.setOnClickListener {

            val nama = etNama.text.toString()
            val nim = etNim.text.toString()
//            val semester = spinnerSemester.selectedItem.toString()
//            val id = spinnerSemester.selectedItemId
            val alamat = etAlamat.text.toString()
            val email = etEmail.text.toString()
            val telepon = etTelepon.text.toString()


            if (nama.isEmpty()) {
                inNama.error = "Masukan nama Mahasiswa"
                return@setOnClickListener
            }

            if (nim.isEmpty()) {
                inNim.error = "Masukan nim Mahasiswa"
                return@setOnClickListener
            }

//            if (id < 1) {
//                Toast.makeText(this, "Pilih semster", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
            if (alamat.isEmpty()) {
                inNama.error = "Masukan alamat Mahasiswa"
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                inNama.error = "Masukan email Mahasiswa"
                return@setOnClickListener
            }
            if (telepon.isEmpty()) {
                inNama.error = "Masukan telepon Mahasiswa"
                return@setOnClickListener
            }

            val data = ModelMahasiswa()
            data.nama = nama
            data.nim = nim.toInt()
//            data.semster = semester
            data.alamat = alamat
            data.email = email
            data.telepon = telepon

            val stat = DatabaseHelper.insertData(data)

            if (stat > 0) {
//                spinnerSemester.setSelection(0)
                etNama.text.clear()
                etNim.text.clear()
                etAlamat.text.clear()
                etEmail.text.clear()
                etTelepon.text.clear()

                etNim.clearFocus()
                etNama.clearFocus()
                etAlamat.clearFocus()
                etEmail.clearFocus()
                etTelepon.clearFocus()

                Toast.makeText(this, "Berhasil Menambah Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Menambah Data", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this, HalamanUtama::class.java))
        }
        btnLihatData.setOnClickListener {
            startActivity(Intent(this, DaftarMahasiswaActivity::class.java))
        }

    }

    private fun getSemester(): List<String> = listOf("SEMESTER", "SEMESTER 1", "SEMESTER 2", "SEMESTER 3", "SEMESTER 4", "SEMESTER 5", "SEMESTER 6", "SEMESTER 7")

    private class Watcher(textinput: TextInputLayout) : TextWatcher {

        val input = textinput

        override fun afterTextChanged(p0: Editable?) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            input.isErrorEnabled = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        DatabaseHelper.closeDatabase()
    }
}