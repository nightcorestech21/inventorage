package com.example.invent_zeva

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TableLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi Firebase
        FirebaseApp.initializeApp(this)
        database = FirebaseDatabase.getInstance().reference.child("inventaris")

        val edNamaPJ = findViewById<EditText>(R.id.edNamaPJ)
        val edJabatan = findViewById<EditText>(R.id.edJabatan)
        val edMerk = findViewById<EditText>(R.id.edMerk)
        val edOS = findViewById<EditText>(R.id.edOS)
        val edSerial = findViewById<EditText>(R.id.edSerial)
        val spinnerRAM = findViewById<Spinner>(R.id.spinRAM)
        val spinnerKondisi = findViewById<Spinner>(R.id.spinnerKondisi)
        val btnAdd = findViewById<Button>(R.id.btn1)
        val tableLayout = findViewById<TableLayout>(R.id.tb1)

        // Opsi RAM untuk Spinner
        val ramOptions = arrayOf("2GB", "4GB", "8GB", "16GB", "32GB")
        val ramAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, ramOptions)
        ramAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRAM.adapter = ramAdapter

        // Opsi Kondisi untuk Spinner
        val kondisiOptions = arrayOf("Baik", "Rusak Ringan", "Rusak Berat")
        val kondisiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, kondisiOptions)
        kondisiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKondisi.adapter = kondisiAdapter

        // Event listener untuk Spinner RAM
        var selectedRAM = ramOptions[0]
        spinnerRAM.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRAM = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Event listener untuk Spinner Kondisi
        var selectedKondisi = kondisiOptions[0]
        spinnerKondisi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedKondisi = parent.getItemAtPosition(position).toString()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        btnAdd.setOnClickListener {
            val namaPJ = edNamaPJ.text.toString()
            val jabatan = edJabatan.text.toString()
            val merk = edMerk.text.toString()
            val os = edOS.text.toString()
            val serial = edSerial.text.toString()

            if (namaPJ.isNotEmpty() && serial.isNotEmpty()) {
                val id = database.push().key ?: "" // Generate key unik

                val item = mapOf(
                    "namaPJ" to namaPJ,
                    "jabatan" to jabatan,
                    "merk" to merk,
                    "os" to os,
                    "serial" to serial,
                    "ram" to selectedRAM, // Pakai nilai Spinner RAM
                    "kondisi" to selectedKondisi // Pakai nilai Spinner Kondisi
                )

                database.child(id).setValue(item).addOnSuccessListener {
                    // Hapus input setelah ditambahkan
                    edNamaPJ.text.clear()
                    edJabatan.text.clear()
                    edMerk.text.clear()
                    edOS.text.clear()
                    edSerial.text.clear()

                    val intent = Intent(this, TabelActivity::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Masukkan Data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
