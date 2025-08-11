package com.example.invent_zeva

import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class TabelActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var tableLayout: TableLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        tableLayout = findViewById(R.id.tb1)
        database = FirebaseDatabase.getInstance().reference.child("inventaris")

        loadData()
    }

    private fun loadData() {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (child in snapshot.children) {
                    val namaPJ = child.child("namaPJ").value.toString()
                    val jabatan = child.child("jabatan").value.toString()
                    val merk = child.child("merk").value.toString()
                    val os = child.child("os").value.toString()
                    val serial = child.child("serial").value.toString()
                    val ram = child.child("ram").value.toString()
                    val kondisi = child.child("kondisi").value.toString()

                    val newRow = TableRow(this@TabelActivity)

                    newRow.addView(createTextView(namaPJ))
                    newRow.addView(createTextView(jabatan))
                    newRow.addView(createTextView(merk))
                    newRow.addView(createTextView(os))
                    newRow.addView(createTextView(serial))
                    newRow.addView(createTextView(ram))
                    newRow.addView(createTextView(kondisi))

                    tableLayout.addView(newRow)
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun createTextView(text: String): TextView {
        return TextView(this).apply {
            this.text = text
            setPadding(8, 8, 8, 8)
        }
    }
}
