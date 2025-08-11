package com.example.invent_zeva

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TabelPrinterActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = android.content.pm.ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_tabel_printer)

        tableLayout = findViewById(R.id.tabelPrinter)
        database = FirebaseDatabase.getInstance().reference.child("inventaris").child("printer")

        loadData()
    }

    private fun loadData() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                tableLayout.removeViews(1, tableLayout.childCount - 1)
                for (data in snapshot.children) {
                    val id = data.key ?: continue
                    val namaPJ = data.child("namaPJ").getValue(String::class.java) ?: ""
                    val jabatan = data.child("jabatan").getValue(String::class.java) ?: ""
                    val merk = data.child("merk").getValue(String::class.java) ?: ""
                    val serial = data.child("serialNumber").getValue(String::class.java) ?: ""
                    val keterangan = data.child("keterangan").getValue(String::class.java) ?: ""
                    val kondisi = data.child("kondisi").getValue(String::class.java) ?: ""

                    val row = TableRow(this@TabelPrinterActivity).apply {
                        addView(makeCell(namaPJ))
                        addView(makeCell(jabatan))
                        addView(makeCell(merk))
                        addView(makeCell(serial))
                        addView(makeCell(keterangan))
                        addView(makeCell(kondisi))
                        addView(makeActions(id))
                    }

                    tableLayout.addView(row)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TabelPrinterActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeCell(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 10f
            gravity = Gravity.CENTER
            setTextColor(Color.BLACK)
            setPadding(8, 6, 8, 6)
        }
    }

    private fun makeActions(id: String): LinearLayout {
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            gravity = Gravity.CENTER
        }

        val editBtn = Button(this).apply {
            text = "Edit"
            setOnClickListener {
                val intent = Intent(this@TabelPrinterActivity, InventPrinterActivity::class.java)
                intent.putExtra("printerId", id)
                startActivity(intent)
            }
        }

        val deleteBtn = Button(this).apply {
            text = "Hapus"
            setOnClickListener {
                AlertDialog.Builder(this@TabelPrinterActivity).apply {
                    setTitle("Hapus Data")
                    setMessage("Yakin ingin menghapus data ini?")
                    setPositiveButton("Ya") { _, _ ->
                        database.child(id).removeValue()
                    }
                    setNegativeButton("Tidak", null)
                    show()
                }
            }
        }

        layout.addView(editBtn)
        layout.addView(deleteBtn)

        return layout
    }
}
