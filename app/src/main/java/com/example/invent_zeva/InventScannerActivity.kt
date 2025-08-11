package com.example.invent_zeva

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InventScannerActivity : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invent_scanner)

        // Ambil view
        val edNamaPJ = findViewById<EditText>(R.id.edScanNamaPJ)
        val edJabatan = findViewById<EditText>(R.id.edScanJabatan)
        val edMerk = findViewById<EditText>(R.id.edScanMerk)
        val edSerial = findViewById<EditText>(R.id.edScanSeri)
        val edType = findViewById<EditText>(R.id.edScanType)
        val edKet = findViewById<EditText>(R.id.edScanKet)
        val edKondisi = findViewById<EditText>(R.id.spinScanKondisi)
        val edNup = findViewById<EditText>(R.id.edScanNUP)
        val btnAdd = findViewById<Button>(R.id.btnAddScanner)

        val dbRef = FirebaseDatabase.getInstance().reference.child("inventaris").child("scanner")

        btnAdd.setOnClickListener {
            val data = mapOf(
                "namaPJ" to edNamaPJ.text.toString(),
                "jabatan" to edJabatan.text.toString(),
                "merk" to edMerk.text.toString(),
                "serialNumber" to edSerial.text.toString(),
                "type" to edType.text.toString(),
                "keterangan" to edKet.text.toString(),
                "kondisi" to edKondisi.text.toString(),
                "nup" to edNup.text.toString()
            )

            val id = dbRef.push().key ?: "scanner_${System.currentTimeMillis()}"
            dbRef.child(id).setValue(data).addOnSuccessListener {
                Toast.makeText(this, "Data scanner ditambahkan!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TabelScannerActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal menambahkan data!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
