package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TabelPCActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference
    private lateinit var btnTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel_pc)

        tableLayout = findViewById(R.id.tabelPC)
        btnTambah = findViewById(R.id.btnTambahPC)

        // ✅ Path yang benar sesuai penyimpanan
        database = FirebaseDatabase.getInstance().getReference("inventaris").child("pc")

        btnTambah.setOnClickListener {
            val intent = Intent(this, InventPCActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }


    private fun loadData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Bersihkan baris tabel kecuali header
                if (tableLayout.childCount > 1) {
                    tableLayout.removeViews(1, tableLayout.childCount - 1)
                }

                for (child in snapshot.children) {
                    val id = child.key ?: continue
                    val namaPJ = child.child("namaPJ").value?.toString() ?: "-"
                    val jabatan = child.child("jabatan").value?.toString() ?: "-"
                    val serialNumber = child.child("serialNumber").value?.toString() ?: "-"
                    val merk = child.child("merk").value?.toString() ?: "-"
                    val os = child.child("os").value?.toString() ?: "-"
                    val ram = child.child("ram").value?.toString() ?: "-"
                    val nup = child.child("nup").value?.toString() ?: "-"
                    val kondisi = child.child("kondisi").value?.toString() ?: "-"
                    val keterangan = child.child("keterangan").value?.toString() ?: "-"

                    // Debug Log (Opsional)
                    Log.d("TabelPC", "Data: $namaPJ, $serialNumber")

                    val row = TableRow(this@TabelPCActivity).apply {
                        addView(makeCell(namaPJ))
                        addView(makeCell(jabatan))
                        addView(makeCell(serialNumber))
                        addView(makeCell(merk))
                        addView(makeCell(os))
                        addView(makeCell(ram))
                        addView(makeCell(nup))
                        addView(makeCell(kondisi))
                        addView(makeCell(keterangan))
                        addView(makeActionButtons(id, namaPJ, jabatan, serialNumber, merk, os, ram, nup, kondisi, keterangan))
                    }

                    tableLayout.addView(row)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TabelPCActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeCell(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 11f
            setPadding(8, 4, 8, 4)
            gravity = Gravity.CENTER
        }
    }

    private fun makeActionButtons(
        id: String,
        namaPJ: String,
        jabatan: String,
        serialNumber: String,
        merk: String,
        os: String,
        ram: String,
        nup: String,
        kondisi: String,
        keterangan: String
    ): LinearLayout {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val btnEdit = Button(this).apply {
            text = "Edit"
            textSize = 10f
            setOnClickListener {
                val intent = Intent(this@TabelPCActivity, EditPCActivity::class.java).apply {
                    putExtra("id", id)
                    putExtra("namaPJ", namaPJ)
                    putExtra("jabatan", jabatan)
                    putExtra("serialNumber", serialNumber)
                    putExtra("merk", merk)
                    putExtra("os", os)
                    putExtra("ram", ram)
                    putExtra("nup", nup)
                    putExtra("kondisi", kondisi)
                    putExtra("keterangan", keterangan)
                }
                startActivity(intent)
            }
        }

        val btnDelete = Button(this).apply {
            text = "Delete"
            textSize = 10f
            setOnClickListener {
                AlertDialog.Builder(this@TabelPCActivity).apply {
                    setTitle("Hapus Data")
                    setMessage("Yakin ingin menghapus data ini?")
                    setPositiveButton("Ya") { _, _ ->
                        database.child(id).removeValue().addOnSuccessListener {
                            Toast.makeText(context, "Data dihapus", Toast.LENGTH_SHORT).show()
                            loadData()
                        }
                    }
                    setNegativeButton("Batal", null)
                    show()
                }
            }
        }

        layout.addView(btnEdit)
        layout.addView(btnDelete)
        return layout
    }
}
