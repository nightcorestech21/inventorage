package com.example.invent_zeva

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class InventLaptopActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invent_laptop)

        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance().reference.child("inventaris").child("laptop")

        val edNamaPJ = findViewById<EditText>(R.id.edLapNamaPJ)
        val edJabatan = findViewById<EditText>(R.id.edLapJabatan)
        val edSerial = findViewById<EditText>(R.id.edLapSE)
        val edMerk = findViewById<EditText>(R.id.edLapMerk)
        val edOS = findViewById<EditText>(R.id.edLapOS)
        val edNUP = findViewById<EditText>(R.id.edLapNUP)
        val edKeterangan = findViewById<EditText>(R.id.edLapKeterangan)
        val spinnerRAM = findViewById<Spinner>(R.id.spinLapRAM)
        val spinnerKondisi = findViewById<Spinner>(R.id.spinLapKondisi)
        val btnAdd = findViewById<Button>(R.id.btnLaptopAdd)

        val ramOptions = arrayOf("2GB", "4GB", "8GB", "16GB", "32GB")
        val kondisiOptions = arrayOf("Baik", "Rusak Ringan", "Rusak Berat")

        spinnerRAM.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ramOptions)
        spinnerKondisi.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kondisiOptions)

        var selectedRAM = ramOptions[0]
        var selectedKondisi = kondisiOptions[0]

        spinnerRAM.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>, v: View?, pos: Int, id: Long) {
                selectedRAM = ramOptions[pos]
            }
            override fun onNothingSelected(p: AdapterView<*>) {}
        }

        spinnerKondisi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p: AdapterView<*>, v: View?, pos: Int, id: Long) {
                selectedKondisi = kondisiOptions[pos]
            }
            override fun onNothingSelected(p: AdapterView<*>) {}
        }

        btnAdd.setOnClickListener {
            val namaPJ = edNamaPJ.text.toString()
            val jabatan = edJabatan.text.toString()
            val serial = edSerial.text.toString()
            val merk = edMerk.text.toString()
            val os = edOS.text.toString()
            val nup = edNUP.text.toString()
            val keterangan = edKeterangan.text.toString()

            if (namaPJ.isNotEmpty() && serial.isNotEmpty()) {
                val data = mapOf(
                    "namaPJ" to namaPJ,
                    "jabatan" to jabatan,
                    "serialNumber" to serial,
                    "merk" to merk,
                    "os" to os,
                    "ram" to selectedRAM,
                    "nup" to nup,
                    "kondisi" to selectedKondisi,
                    "keterangan" to keterangan
                )

                val id = database.push().key ?: "unknown_laptop_id"
                database.child(id).setValue(data).addOnSuccessListener {
                    Toast.makeText(this, "Data Laptop ditambahkan!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TabelLaptopActivity::class.java))
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal menambahkan data!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Nama PJ & Serial wajib diisi", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
