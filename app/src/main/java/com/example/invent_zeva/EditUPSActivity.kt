package com.example.invent_zeva

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EditUPSActivity : AppCompatActivity() {

    private lateinit var dbRef: DatabaseReference
    private var itemId: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ups)

        val edSerial = findViewById<EditText>(R.id.editUPSSerial)
        val edMerk = findViewById<EditText>(R.id.editUPSMerk)
        val edKondisi = findViewById<EditText>(R.id.editUPSKondisi)
        val edNUP = findViewById<EditText>(R.id.editUPSNup)
        val edSerialPC = findViewById<EditText>(R.id.editUPSSerialPC)
        val btnUpdate = findViewById<Button>(R.id.btnUpdateUPS)

        itemId = intent.getStringExtra("id")
        dbRef = FirebaseDatabase.getInstance().getReference("inventaris").child("ups")

        if (itemId != null) {
            dbRef.child(itemId!!).addListenerForSingleValueEvent(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    edSerial.setText(snapshot.child("serialNumber").value.toString())
                    edMerk.setText(snapshot.child("merk").value.toString())
                    edKondisi.setText(snapshot.child("kondisi").value.toString())
                    edNUP.setText(snapshot.child("nup").value.toString())
                    edSerialPC.setText(snapshot.child("serialNumberPC").value.toString())
                }

                override fun onCancelled(error: DatabaseError) {}
            })
        }

        btnUpdate.setOnClickListener {
            val updatedData = mapOf(
                "serialNumber" to edSerial.text.toString(),
                "merk" to edMerk.text.toString(),
                "kondisi" to edKondisi.text.toString(),
                "nup" to edNUP.text.toString(),
                "serialNumberPC" to edSerialPC.text.toString()
            )

            dbRef.child(itemId!!).updateChildren(updatedData).addOnSuccessListener {
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Gagal memperbarui data", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
