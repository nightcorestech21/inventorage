package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class InventUPSActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invent_ups)

        FirebaseApp.initializeApp(this)
        val database = FirebaseDatabase.getInstance().reference.child("inventaris").child("ups")

        val edSerial = findViewById<EditText>(R.id.edUPSSerialNumber)
        val edMerk = findViewById<EditText>(R.id.edUPSMerk)
        val edKondisi = findViewById<EditText>(R.id.edUPSKondisi)
        val edNUP = findViewById<EditText>(R.id.edUPSNup)
        val edSerialPC = findViewById<EditText>(R.id.edUPSSeriNumPC)
        val btnAdd = findViewById<Button>(R.id.UPSTambah)

        btnAdd.setOnClickListener {
            val serial = edSerial.text.toString()
            val merk = edMerk.text.toString()
            val kondisi = edKondisi.text.toString()
            val nup = edNUP.text.toString()
            val serialPC = edSerialPC.text.toString()

            if (serial.isNotEmpty()) {
                val data = mapOf(
                    "serialNumber" to serial,
                    "merk" to merk,
                    "kondisi" to kondisi,
                    "nup" to nup,
                    "serialNumberPC" to serialPC
                )

                val id = database.push().key ?: "unknown_ups_id"
                database.child(id).setValue(data).addOnSuccessListener {
                    Toast.makeText(this, "Data UPS ditambahkan!", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, TabelUPSActivity::class.java))
                    finish()
                    edSerial.text.clear()
                    edMerk.text.clear()
                    edKondisi.text.clear()
                    edNUP.text.clear()
                    edSerialPC.text.clear()
                }.addOnFailureListener {
                    Toast.makeText(this, "Gagal menambahkan data!", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Serial Number wajib diisi!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
