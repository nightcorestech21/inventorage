package com.example.invent_zeva

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TabelUPSActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var dbRef: DatabaseReference
    private lateinit var btnTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel_ups)

        tableLayout = findViewById(R.id.tabelUPS)
        btnTambah = findViewById(R.id.btnTambahUPS) // ID dari tombol di XML

        // Firebase reference: inventaris/ups
        dbRef = FirebaseDatabase.getInstance().reference.child("inventaris").child("ups")

        btnTambah.setOnClickListener {
            // Arahkan ke form tambah UPS
            val intent = Intent(this, InventUPSActivity::class.java)
            startActivity(intent)
        }

        // Load data dari Firebase
        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Hapus semua baris kecuali header (indeks 0)
                tableLayout.removeViews(1, tableLayout.childCount - 1)

                for (child in snapshot.children) {
                    val serial = child.child("serialNumber").value?.toString() ?: "-"
                    val merk = child.child("merk").value?.toString() ?: "-"
                    val kondisi = child.child("kondisi").value?.toString() ?: "-"
                    val nup = child.child("nup").value?.toString() ?: "-"
                    val serialPC = child.child("serialNumberPC").value?.toString() ?: "-"

                    val row = TableRow(this@TabelUPSActivity)
                    row.addView(makeCell(serial))
                    row.addView(makeCell(merk))
                    row.addView(makeCell(kondisi))
                    row.addView(makeCell(nup))
                    row.addView(makeCell(serialPC))

                    tableLayout.addView(row)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TabelUPSActivity, "Gagal memuat data!", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeCell(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            setPadding(8, 8, 8, 8)
            textSize = 12f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            setBackgroundColor(Color.parseColor("#F2F2F2"))
        }
    }
}
