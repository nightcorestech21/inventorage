package com.example.invent_zeva

import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class FilterHardwareActivity : AppCompatActivity() {

    private lateinit var spinFungsi: Spinner
    private lateinit var btnTerapkan: Button
    private lateinit var layoutHasil: LinearLayout
    private lateinit var database: DatabaseReference

    private val fungsiOptions = arrayOf(
        "Tata Usaha", "Nerwilis", "Sosial", "Distribusi", "Produksi", "Kepala", "Mitra"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter_hardware)

        spinFungsi = findViewById(R.id.spinFilterFungsi)
        btnTerapkan = findViewById(R.id.btnFilterTerapkan)
        layoutHasil = findViewById(R.id.layoutHasilFilter)

        spinFungsi.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, fungsiOptions)

        database = FirebaseDatabase.getInstance().getReference("layanan_hardware")

        btnTerapkan.setOnClickListener {
            val selectedFungsi = spinFungsi.selectedItem.toString()
            filterData(selectedFungsi)
        }
    }

    private fun filterData(fungsi: String) {
        layoutHasil.removeAllViews()

        database.orderByChild("keberadaan").equalTo(fungsi)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (child in snapshot.children) {
                            val pelapor = child.child("namaPelapor").value?.toString() ?: "-"
                            val tanggal = child.child("tanggalKejadian").value?.toString() ?: "-"
                            val hardware = child.child("namaHardware").value?.toString() ?: "-"
                            val jenis = child.child("jenisHardware").value?.toString() ?: "-"

                            val textView = TextView(this@FilterHardwareActivity).apply {
                                text = """
                                    Pelapor: $pelapor
                                    Tanggal: $tanggal
                                    Nama Hardware: $hardware
                                    Jenis Hardware: $jenis
                                """.trimIndent()
                                textSize = 13f
                                setPadding(12, 8, 12, 8)
                                gravity = Gravity.START
                                setBackgroundResource(android.R.drawable.dialog_holo_light_frame)
                            }
                            layoutHasil.addView(textView)
                        }
                    } else {
                        val textView = TextView(this@FilterHardwareActivity).apply {
                            text = "Tidak ada data untuk fungsi $fungsi"
                            textSize = 14f
                            setPadding(12, 8, 12, 8)
                        }
                        layoutHasil.addView(textView)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@FilterHardwareActivity, "Gagal memuat data", Toast.LENGTH_SHORT).show()
                }
            })
    }
}
