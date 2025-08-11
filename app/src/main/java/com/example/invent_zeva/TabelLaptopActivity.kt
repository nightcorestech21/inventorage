package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TabelLaptopActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference
    private lateinit var btnTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tabel_laptop)

        tableLayout = findViewById(R.id.tabelLaptop)
        btnTambah = findViewById(R.id.btnTambahLaptop)

        database = FirebaseDatabase.getInstance().getReference("laptop")

        btnTambah.setOnClickListener {
            val intent = Intent(this, InventLaptopActivity::class.java)
            startActivity(intent)
        }

        loadData()
    }

    private fun loadData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tableLayout.removeViews(1, tableLayout.childCount - 1)

                for (child in snapshot.children) {
                    val id = child.key ?: continue
                    val namaPJ = child.child("namaPJ").value?.toString() ?: "-"
                    val jabatan = child.child("jabatan").value?.toString() ?: "-"
                    val serial = child.child("serialNumber").value?.toString() ?: "-"
                    val merk = child.child("merk").value?.toString() ?: "-"
                    val os = child.child("os").value?.toString() ?: "-"
                    val ram = child.child("ram").value?.toString() ?: "-"
                    val nup = child.child("nup").value?.toString() ?: "-"
                    val kondisi = child.child("kondisi").value?.toString() ?: "-"
                    val keterangan = child.child("keterangan").value?.toString() ?: "-"

                    val row = TableRow(this@TabelLaptopActivity).apply {
                        addView(makeCell(namaPJ))
                        addView(makeCell(jabatan))
                        addView(makeCell(serial))
                        addView(makeCell(merk))
                        addView(makeCell(os))
                        addView(makeCell(ram))
                        addView(makeCell(nup))
                        addView(makeCell(kondisi))
                        addView(makeCell(keterangan))
                        addView(makeActionButtons(id, namaPJ, jabatan, serial, merk, os, ram, nup, kondisi, keterangan))
                    }

                    tableLayout.addView(row)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TabelLaptopActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
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
        serial: String,
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
                val intent = Intent(this@TabelLaptopActivity, EditLaptopActivity::class.java)
                intent.putExtra("id", id)
                intent.putExtra("namaPJ", namaPJ)
                intent.putExtra("jabatan", jabatan)
                intent.putExtra("serialNumber", serial)
                intent.putExtra("merk", merk)
                intent.putExtra("os", os)
                intent.putExtra("ram", ram)
                intent.putExtra("nup", nup)
                intent.putExtra("kondisi", kondisi)
                intent.putExtra("keterangan", keterangan)
                startActivity(intent)
            }
        }

        val btnDelete = Button(this).apply {
            text = "Delete"
            textSize = 10f
            setOnClickListener {
                AlertDialog.Builder(this@TabelLaptopActivity).apply {
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
