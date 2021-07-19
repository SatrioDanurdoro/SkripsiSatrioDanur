package com.example.skripsiku

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.skripsiku.database.DatabaseHelper
import com.example.skripsiku.model.ModelMahasiswa
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.activity_update_mahasiswa.*

class UpdateDataMahasiswaActivity : AppCompatActivity() {
    var dataMahasiswa = ModelMahasiswa()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_mahasiswa)

        bindView()

        etNamaEdit.addTextChangedListener(Watcher(inNamaEdit))
        etNimEdit.addTextChangedListener(Watcher(inNimEdit))

        btnEdit.setOnClickListener {

            val nama = etNamaEdit.text.toString()
            val nim = etNimEdit.text.toString()
            val email = etEmailEdit.text.toString()
            val alamat = etAlamatEdit.text.toString()
            val telepon = etTeleponEdit.text.toString()

            if (nama.isEmpty()) {
                inNamaEdit.error = "Masukan nama Mahasiswa"
                return@setOnClickListener
            }

            if (nim.isEmpty()) {
                inNimEdit.error = "Masukan nim Mahasiswa"
                return@setOnClickListener
            }


            if (alamat.isEmpty()) {
                inNamaEdit.error = "Masukan alamat Mahasiswa"
                return@setOnClickListener
            }

            if (email.isEmpty()) {
                inNamaEdit.error = "Masukan email Mahasiswa"
                return@setOnClickListener
            }

            if (telepon.isEmpty()) {
                inNamaEdit.error = "Masukan telepon Mahasiswa"
                return@setOnClickListener
            }

            dataMahasiswa.nama = nama
            dataMahasiswa.nim = nim.toInt()
            dataMahasiswa.alamat = alamat
            dataMahasiswa.email = email
            dataMahasiswa.telepon = telepon

            val stat = DatabaseHelper.updateData(dataMahasiswa)

            if (stat > 0) {
                val bind = Bundle()
                bind.putParcelable("DATA", dataMahasiswa)

                val intent = Intent()
                intent.putExtras(bind)

                setResult(Activity.RESULT_OK, intent)

                Toast.makeText(this, "Berhasil Mengupdate Data", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Gagal Mengudate Data", Toast.LENGTH_SHORT).show()
            }
            startActivity(Intent(this, HalamanUtama::class.java))
        }
        toolbarEdit.title = "Update Data Mahasiswa"
    }

    private fun bindView() {
        val bind = intent.extras
        if (bind != null) {
            dataMahasiswa = bind.getParcelable("DATA")!!
        }

        etNamaEdit.setText(dataMahasiswa.nama)
        etNimEdit.setText(dataMahasiswa.nim.toString())

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, getSemester())
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
