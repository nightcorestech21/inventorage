package com.example.invent_zeva

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TampilLayananSoftwareActivity : AppCompatActivity() {

    private lateinit var tableLayout: TableLayout
    private lateinit var database: DatabaseReference
    private lateinit var btnTambah: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tampil_layanan_software)

        tableLayout = findViewById(R.id.tabelLayananSoftware)
        btnTambah = findViewById(R.id.btnTambahLayananSoftware)

        database = FirebaseDatabase.getInstance().getReference("layanan_software")

        btnTambah.setOnClickListener {
            val intent = Intent(this, AddLayananSoftwareActivity::class.java)
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
                    val keberadaan = child.child("keberadaan").value?.toString() ?: "-"
                    val pelapor = child.child("namaPelapor").value?.toString() ?: "-"
                    val tanggal = child.child("tanggal").value?.toString() ?: "-"
                    val software = child.child("software").value?.toString() ?: "-"
                    val hardware = child.child("hardware").value?.toString() ?: "-"

                    val row = TableRow(this@TampilLayananSoftwareActivity).apply {
                        addView(makeText(keberadaan))
                        addView(makeText(pelapor))
                        addView(makeText(tanggal))
                        addView(makeText(software))
                        addView(makeText(hardware))
                        addView(makeDeleteButton(id))
                    }

                    tableLayout.addView(row)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@TampilLayananSoftwareActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun makeText(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            textSize = 11f
            gravity = Gravity.CENTER
            setPadding(8, 4, 8, 4)
        }
    }

    private fun makeDeleteButton(id: String): LinearLayout {
        val layout = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }

        val btnDelete = Button(this).apply {
            text = "Hapus"
            textSize = 10f
            setOnClickListener {
                AlertDialog.Builder(this@TampilLayananSoftwareActivity).apply {
                    setTitle("Konfirmasi")
                    setMessage("Hapus data layanan ini?")
                    setPositiveButton("Ya") { _, _ ->
                        database.child(id).removeValue().addOnSuccessListener {
                            Toast.makeText(context, "Data dihapus", Toast.LENGTH_SHORT).show()
                            loadData()
                        }
                    }
                    setNegativeButton("Tidak", null)
                    show()
                }
            }
        }

        layout.addView(btnDelete)
        return layout
    }
}