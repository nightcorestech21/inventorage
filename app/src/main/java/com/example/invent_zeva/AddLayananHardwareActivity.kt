package com.example.invent_zeva

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddLayananHardwareActivity : AppCompatActivity() {

    private lateinit var spinnerFungsi: Spinner
    private lateinit var edNamaPelapor: EditText
    private lateinit var edTglKejadian: EditText
    private lateinit var edNamaHardware: EditText
    private lateinit var edJenisHardware: EditText
    private lateinit var btnTambah: Button

    private val fungsiOptions = arrayOf(
        "Tata Usaha", "Nerwilis", "Sosial", "Distribusi", "Produksi", "Kepala", "Mitra"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_layanan_hardware)

        spinnerFungsi = findViewById(R.id.spinHardFungsi)
        edNamaPelapor = findViewById(R.id.edHardNamaPelapor)
        edTglKejadian = findViewById(R.id.edHardTglKejadian)
        edNamaHardware = findViewById(R.id.edHardNamaHardware)
        edJenisHardware = findViewById(R.id.edHardJenisHardware)
        btnTambah = findViewById(R.id.btnHardAdd)

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, fungsiOptions)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFungsi.adapter = adapter

        edTglKejadian.setOnClickListener {
            showDatePicker()
        }

        btnTambah.setOnClickListener {
            tambahData()
        }
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                val dateString = String.format("%02d-%02d-%d", dayOfMonth, month + 1, year)
                edTglKejadian.setText(dateString)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun tambahData() {
        val keberadaan = spinnerFungsi.selectedItem.toString()
        val namaPelapor = edNamaPelapor.text.toString().trim()
        val tglKejadian = edTglKejadian.text.toString().trim()
        val namaHardware = edNamaHardware.text.toString().trim()
        val jenisHardware = edJenisHardware.text.toString().trim()

        if (namaPelapor.isEmpty() || tglKejadian.isEmpty() || namaHardware.isEmpty() || jenisHardware.isEmpty()) {
            Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val ref = FirebaseDatabase.getInstance().getReference("layanan_hardware")
        val id = ref.push().key ?: return

        val data = mapOf(
            "keberadaan" to keberadaan,
            "namaPelapor" to namaPelapor,
            "tanggalKejadian" to tglKejadian,
            "namaHardware" to namaHardware,
            "jenisHardware" to jenisHardware
        )

        ref.child(id).setValue(data).addOnSuccessListener {
            Toast.makeText(this, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show()
            finish()
        }.addOnFailureListener {
            Toast.makeText(this, "Gagal menambahkan data", Toast.LENGTH_SHORT).show()
        }
    }
}
