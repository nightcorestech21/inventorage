package com.example.invent_zeva

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EditPCActivity : AppCompatActivity() {

    private lateinit var edtNamaPJ: EditText
    private lateinit var edtJabatan: EditText
    private lateinit var edtSerial: EditText
    private lateinit var edtMerk: EditText
    private lateinit var edtOS: EditText
    private lateinit var edtNUP: EditText
    private lateinit var edtKeterangan: EditText
    private lateinit var spinRAM: Spinner
    private lateinit var spinKondisi: Spinner
    private lateinit var btnUpdate: Button

    private var dataId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_pc)

        // Binding view
        edtNamaPJ = findViewById(R.id.UpdatePCNamaPJ)
        edtJabatan = findViewById(R.id.UpdatePCJabatan)
        edtSerial = findViewById(R.id.UpdatePCSeriNumber)
        edtMerk = findViewById(R.id.UpdatePCMerk)
        edtOS = findViewById(R.id.UpdatePCOS)
        edtNUP = findViewById(R.id.UpdatePCNUP)
        edtKeterangan = findViewById(R.id.UpdatePCKeterangan)
        spinRAM = findViewById(R.id.spinUpdatePCRAM)
        spinKondisi = findViewById(R.id.spinUpdatePCKondisi)
        btnUpdate = findViewById(R.id.btnPCUpdate)

        // Spinner setup
        val ramOptions = arrayOf("2GB", "4GB", "8GB", "16GB", "32GB")
        val kondisiOptions = arrayOf("Baik", "Rusak Ringan", "Rusak Berat")

        spinRAM.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, ramOptions)
        spinKondisi.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, kondisiOptions)

        // Ambil data dari intent
        val intent = intent
        dataId = intent.getStringExtra("id")

        edtNamaPJ.setText(intent.getStringExtra("namaPJ"))
        edtJabatan.setText(intent.getStringExtra("jabatan"))
        edtSerial.setText(intent.getStringExtra("serialNumber"))
        edtMerk.setText(intent.getStringExtra("merk"))
        edtOS.setText(intent.getStringExtra("os"))
        edtNUP.setText(intent.getStringExtra("nup"))
        edtKeterangan.setText(intent.getStringExtra("keterangan"))

        val ramValue = intent.getStringExtra("ram")
        val kondisiValue = intent.getStringExtra("kondisi")

        spinRAM.setSelection(ramOptions.indexOf(ramValue))
        spinKondisi.setSelection(kondisiOptions.indexOf(kondisiValue))

        btnUpdate.setOnClickListener {
            updateData()
        }
    }

    private fun updateData() {
        val database = FirebaseDatabase.getInstance().getReference("inventaris").child("pc")

        val updatedData = mapOf(
            "namaPJ" to edtNamaPJ.text.toString(),
            "jabatan" to edtJabatan.text.toString(),
            "serialNumber" to edtSerial.text.toString(),
            "merk" to edtMerk.text.toString(),
            "os" to edtOS.text.toString(),
            "ram" to spinRAM.selectedItem.toString(),
            "nup" to edtNUP.text.toString(),
            "kondisi" to spinKondisi.selectedItem.toString(),
            "keterangan" to edtKeterangan.text.toString()
        )

        dataId?.let {
            database.child(it).updateChildren(updatedData).addOnSuccessListener {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal update data", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
