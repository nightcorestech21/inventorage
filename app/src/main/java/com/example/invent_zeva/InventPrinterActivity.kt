package com.example.invent_zeva

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class InventPrinterActivity : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invent_printer)
        enableEdgeToEdge()

        val edNamaPJ = findViewById<EditText>(R.id.edPrintNamaPJ)
        val edJabatan = findViewById<EditText>(R.id.edPrinterJabatan)
        val edMerk = findViewById<EditText>(R.id.edPrinterMerk)
        val edSerial = findViewById<EditText>(R.id.edPrinterSeriNum)
        val edKeterangan = findViewById<EditText>(R.id.edPrinterKet)
        val edKondisi = findViewById<EditText>(R.id.spinPrinterKondisi)
        val btnAdd = findViewById<Button>(R.id.btnAddPrint)

        val dbRef = FirebaseDatabase.getInstance().reference.child("inventaris").child("printer")

        btnAdd.setOnClickListener {
            val data = mapOf(
                "namaPJ" to edNamaPJ.text.toString(),
                "jabatan" to edJabatan.text.toString(),
                "merk" to edMerk.text.toString(),
                "serialNumber" to edSerial.text.toString(),
                "keterangan" to edKeterangan.text.toString(),
                "kondisi" to edKondisi.text.toString()
            )

            val id = dbRef.push().key ?: "unknown_id"
            dbRef.child(id).setValue(data).addOnSuccessListener {
                Toast.makeText(this, "Data printer ditambahkan!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, TabelPrinterActivity::class.java))
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
